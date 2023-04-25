package tarladalal;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

//record Recipe (
//		String recipeID, 
//		String recipeName, 
//		ArrayList<RecipeCategory> recipeCategory,
//		ArrayList<FoodCategory> foodCategory,
//		List<String> ingredients,
//		String preparationTime,
//		String cookingTime,
//		List<String> preparationMethod,
//		HashMap<String, String> nutrientValues,
//		TargettedMorbidConditions morbidCondition,
//		String recipeURL
//	) {}

public class RecipeScrape {
/*	public static Recipe recipeCreate(WebDriver driver, String URL, 
			List<String> eliminatedList, List<String> breakfast) throws IOException {
		System.out.println(URL);
        String recipeID = URL.replaceAll("[^0-9]", "");
        
		driver.get(URL);
		String recipeName = driver.findElement(By.id("ctl00_cntrightpanel_lblRecipeName")).getText();
		ArrayList<RecipeCategory> recipeCat = new ArrayList<RecipeCategory>();
		ArrayList<FoodCategory> foodCat = new ArrayList<FoodCategory>();
		if(breakfast.contains(recipeID)) {
			recipeCat.add(RecipeCategory.Breakfast);
		} 
//		if(driver.findElement(By.xpath("//a[contains(text(),'lunch')]")).isDisplayed()) {
			recipeCat.add(RecipeCategory.Lunch);
//		}
		
		By breadCrumb = By.cssSelector("div.breadcrumb span:nth-child(odd)");
//		if(driver.findElements(breadCrumb)
//				.stream().anyMatch(e->e.getText().contains("Veg"))) {
			foodCat.add(FoodCategory.Vegetarian);
//		}
		
		List<String> ingredients = driver.findElements(By.xpath("//div[@id='rcpinglist']//a/span")).stream()
				.map(e->e.getText()).collect(Collectors.toList());
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
			String input = driver.findElement(By.xpath("//div[@id='ctl00_cntrightpanel_pnlRecipeScale']//p[2]"))
					.getText();
			String output2 = input.trim().split("Cooking Time: ")[1];
	        cookingTime = output2.split(" ")[0]+" "+ output2.split(" ")[1];
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
		
		boolean isIncludedDiabetes = !ingredients.stream()
				.anyMatch(y-> eliminatedList.stream().anyMatch(x->StringUtils.containsIgnoreCase(y,x)));
		TargettedMorbidConditions targetMorbid ;
		if(isIncludedDiabetes) {targetMorbid = TargettedMorbidConditions.Diabetes; }
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Recipe cabbageParatha = new Recipe(recipeID,
				recipeName, 
				recipeCat,
				foodCat,
				ingredients,
				preparationTime,
				cookingTime,
				preparationMethod,
				nutrients,
				TargettedMorbidConditions.Diabetes,
				URL
				);
		System.out.println(cabbageParatha.toString());
		return cabbageParatha;
	}  */
	public static void main(String[] args) {
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		ops.addArguments("--headless");
		ops.setExperimentalOption("detach", true);

		WebDriver driver=new ChromeDriver(ops);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		String URL = "https://www.tarladalal.com/maharashtrian-kothimbir-vadi-recipe-deep--fried-42069r";
		driver.get(URL);
//		List<String> ingredients = driver.findElements(By.xpath("//div[@id='rcpinglist']//a/span")).stream()
//				.map(e->e.getText()).collect(Collectors.toList());
		List<WebElement> ingredientsText = driver.findElements(By.xpath("//div[@id='rcpinglist']/div"));
		List<String> textList = ingredientsText.stream().map(e->e.getText())
					.collect(Collectors.toList());
		System.out.println(textList);
//		ingredients.addAll(textList);
//		List<String> finalText = ingredients.stream().distinct().collect(Collectors.toList());
//		System.out.println(finalText);
	}
}
