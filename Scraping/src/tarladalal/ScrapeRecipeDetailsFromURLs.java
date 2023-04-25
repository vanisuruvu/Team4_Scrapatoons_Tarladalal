package tarladalal;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import EnumDataObjects.FoodCategory;
import EnumDataObjects.RecipeCategory;
import EnumDataObjects.TargettedMorbidConditions;


record Recipe (
		String recipeID, 
		String recipeName, 
		ArrayList<RecipeCategory> recipeCategory,
		ArrayList<FoodCategory> foodCategory,
		List<String> ingredients,
		String preparationTime,
		String cookingTime,
		List<String> preparationMethod,
		HashMap<String, String> nutrientValues,
		TargettedMorbidConditions morbidCondition,
		String recipeURL
	) {}

public class ScrapeRecipeDetailsFromURLs {

	public static void main(String[] args) throws IOException {
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
        ops.addArguments("--headless");
		ops.setExperimentalOption("detach", true);
        
		WebDriver driver=new ChromeDriver(ops);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		String path=".\\dataFiles\\tarladalal.xlsx";
		XLUtility xlutil=new XLUtility(path);
		
		//Write headers in excel sheet
		xlutil.setCellData("Sheet1", 0,0, "Recipe ID");
		xlutil.setCellData("Sheet1", 0,1, "Recipe Name");
		xlutil.setCellData("Sheet1", 0,2, "Recipe Category(Breakfast/lunch/snack/dinner)");
		xlutil.setCellData("Sheet1", 0,3, "Food Category(Veg/non-veg/vegan/Jain)");
		xlutil.setCellData("Sheet1", 0,4, "Ingredients");
		xlutil.setCellData("Sheet1", 0,5, "Preparation Time");
		xlutil.setCellData("Sheet1", 0,6, "Cooking Time");
		xlutil.setCellData("Sheet1", 0,7, "Preparation method");
		xlutil.setCellData("Sheet1", 0,8, "Nutrient values");
		xlutil.setCellData("Sheet1", 0,9, "Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		xlutil.setCellData("Sheet1", 0,10, "Recipe URL");
		
		//capture table rows
		List<String> URLList = XLUtility.getData("AllRecipeURLs.xlsx", "Sheet1");
//		URLList.stream()  //.parallel()
//		.forEach(e-> {
//			try {
//				recipeList.add(RecipeScrape
//					.recipeCreate(driver, e, eliminatedList, breakfast));
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		});
		try {
		IntStream.range(1, URLList.size()+1)  //.parallel()
			.forEach(r -> {
				try {
				Recipe recipe = basicRecipeCreate(driver, URLList.get(r-1));
				//read data from web table
				String RecipeID=recipe.recipeID();
				String RecipeName=recipe.recipeName();
				String RecipeCategory=recipe.recipeCategory().toString();
				String FoodCategory=recipe.foodCategory().toString();
				String Ingredients=recipe.ingredients().toString();
				String PreparationTime=recipe.preparationTime();
				String CookingTime=recipe.cookingTime();
				String PreparationMethod=recipe.preparationMethod().toString();
				String NutrientValues=recipe.nutrientValues().toString();
				String TargettedMorbidConditions=recipe.morbidCondition().toString();
				String RecipeURL=recipe.recipeURL();
				System.out.println(RecipeID+RecipeName+RecipeCategory+FoodCategory+Ingredients+PreparationTime+CookingTime+PreparationMethod+NutrientValues+TargettedMorbidConditions+RecipeURL);
				
				//writing the data in excel sheet
				
				xlutil.setCellData("Sheet1", r, 0, RecipeID);
				xlutil.setCellData("Sheet1", r, 1, RecipeName);
				xlutil.setCellData("Sheet1", r, 2, RecipeCategory);
				xlutil.setCellData("Sheet1", r, 3, FoodCategory);
				xlutil.setCellData("Sheet1", r, 4, Ingredients);
				xlutil.setCellData("Sheet1", r, 5, PreparationTime);
				xlutil.setCellData("Sheet1", r, 6, CookingTime);
				xlutil.setCellData("Sheet1", r, 7, PreparationMethod);
				xlutil.setCellData("Sheet1", r, 8, NutrientValues);
				xlutil.setCellData("Sheet1", r, 9, TargettedMorbidConditions );
				xlutil.setCellData("Sheet1", r, 10, RecipeURL );
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Web scrapping is done succesfully.....");
	}

	private static Recipe basicRecipeCreate(WebDriver driver, String url) {
		System.out.println(url);
		driver.get(url);
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		if(wait.until(ExpectedConditions.alertIsPresent())!=null) {
//		    driver.switchTo().alert().accept();
//		}
		try{
		    Alert alert = driver.switchTo().alert();
//		    System.out.println(alert.getText()+" Alert is Displayed"); 
		    driver.switchTo().alert().accept();
		    }
		    catch(Exception ex){
//		    System.out.println("Alert is NOT Displayed");
		    	
		    }
		String recipeID = url.replaceAll("[^0-9]", "");
		String recipeName = driver.findElement(By.id("ctl00_cntrightpanel_lblRecipeName")).getText(); // RecipeName
		List<String> ingredients = driver.findElements(By.xpath("//div[@id='rcpinglist']//a/span")).stream()
				.map(e->e.getText()).collect(Collectors.toList()); // ingredients
		String preparationTime = "";
		try {
		preparationTime = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
		}catch(Exception e) {
			String input = driver.findElement(By.xpath("//div[@id='ctl00_cntrightpanel_pnlRecipeScale']//p[2]"))
					.getText();
			preparationTime = input.trim().split(" ")[2] + " " +input.trim().split(" ")[3];
		}
		String cookingTime = "";
		try{driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();
		}catch(Exception e) {
			try {
			String input = driver.findElement(By.xpath("//div[@id='ctl00_cntrightpanel_pnlRecipeScale']//p[2]"))
					.getText();
			String output2 = input.trim().split("Cooking Time: ")[1];
	        cookingTime = output2.split(" ")[0]+" "+ output2.split(" ")[1];
			}catch(ArrayIndexOutOfBoundsException e2) {
				String input = driver.findElement(By.xpath("//div[@id='ctl00_cntrightpanel_pnlRecipeScale']//p[2]"))
						.getText();
//				System.out.println(input);
				String output2 = input.trim().split("Cooking Time : ")[1];
		        cookingTime = output2.split(" ")[0]+" "+ output2.split(" ")[1];
			}catch(Exception e3) {
				String input = driver.findElement(By.xpath("//div[@id='ctl00_cntrightpanel_pnlRecipeScale']//p[2]"))
						.getText();
//				System.out.println(input);
				String output2 = input.trim().split("CookingTime: ")[1];
		        cookingTime = output2.split(" ")[0]+" "+ output2.split(" ")[1];
				e3.printStackTrace();

// 				https://www.tarladalal.com/cheesy-corn-stuffed-jacket-potatoes--quick-snacks-recipe-2917r
//				https://www.tarladalal.com/makai-paatal-bhaji--healthy-subzi-6415r
//				https://www.tarladalal.com/masoor-dal-with-rice-1388r
//				https://www.tarladalal.com/mexican-bean-fajita-815r
//				https://www.tarladalal.com/paneer-desi-khana-1447r
//				https://www.tarladalal.com/spring-roll-wrappers-1356r
//				https://www.tarladalal.com/strawberry-ice--cream-1907r
//				https://www.tarladalal.com/vegetable-pulao--weight-loss-after-pregnancy--33043r
//				https://www.tarladalal.com/vegetables-in-hot-garlic-sauce--chinese-cooking--321r


			}
		}


		List<String> preparationMethod = driver.findElements(By.xpath("//div[@id='recipe_small_steps']//li[@itemprop = 'itemListElement']/span")).stream()
						.map(e->e.getText()).collect(Collectors.toList());

		List<WebElement> nutritionRows = driver.findElements(By.xpath("//table[@id='rcpnutrients']//tr")); 
		HashMap<String, String> nutrients = new HashMap<String,String>();
		try {
		nutritionRows.stream().forEach(e-> nutrients.put(e.findElement(By.xpath("td[1]")).getText(),
				e.findElement(By.xpath("td[2]/span")).getText()));
		}catch(Exception e1) {
			nutritionRows.stream().forEach(e-> nutrients.put(e.findElement(By.xpath("td[1]")).getText(),
					e.findElement(By.xpath("td[2]")).getText()));
		}
		Recipe recipe = new Recipe(
			recipeID, // recipeID
			recipeName,
			new ArrayList<RecipeCategory>(),
			new ArrayList<FoodCategory>(),
			ingredients,
			preparationTime,
			cookingTime,
			preparationMethod,
			nutrients,
			TargettedMorbidConditions.Diabetes,
			url		
		);
		return recipe;
	}
}