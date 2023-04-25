package FoodCategory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import tarladalal.XLUtility;

public class VegetarianRecipeIDList {

	public static WebDriver driver;

	public static void main(String[] args) throws Exception {

		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		ops.addArguments("--headless");
		ops.setExperimentalOption("detach", true);

		WebDriver driver=new ChromeDriver(ops);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
//		String URL = "https://www.tarladalal.com/recipes-for-breakfast--indian-veg-breakfast-recipes-151";
		List<String> urlList = Arrays.asList(
				"https://www.tarladalal.com/recipes-for-chinese-veg-chinese-recipes-77",
				"https://www.tarladalal.com/recipes-for-thai-veg-thai-vegetarian-recipes-87",
				"https://www.tarladalal.com/recipes-for-mexican-vegetarian-mexican-recipes--96",
				"https://www.tarladalal.com/recipes-for-vegetarian-mexican-starters-100",
				"https://www.tarladalal.com/recipes-for-italian-vegetarian-italian-recipes-105",
				"https://www.tarladalal.com/recipes-for-lebanese-vegetarian-lebanese-recipes-115",
				"https://www.tarladalal.com/recipes-for-main-one-dish-vegetarian-meals-239",
				"https://www.tarladalal.com/recipes-for-quick-soups-326",
				"https://www.tarladalal.com/recipes-for-quick-rice-330",
				"https://www.tarladalal.com/recipes-for-easy-vegetarian-curry-899"
				);
		List<String> onePageUrlList = Arrays.asList(
				"https://www.tarladalal.com/recipes-for-thai-veg-salads-91",
				"https://www.tarladalal.com/recipes-for-vegetarian-mexican-salads-99",
				"https://www.tarladalal.com/recipes-for-high-fiber-vegetables-824",
				"https://www.tarladalal.com/recipes-using-vegetarian-seasoning-cubes-610",
				"https://www.tarladalal.com/recipes-using-vegetarian-oyster-sauce-560"
				);
		for(String URL: urlList) {
			System.out.println(URL);
			driver.navigate().to(URL);
	
			List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
			ArrayList<String> tempList = (ArrayList<String>)getURLListAtoZURL(driver, URL);
			finalList.add(tempList);
			List<String> veganRecipeIDs = finalList.stream().flatMap(e->e.stream()).collect(Collectors.toList());
			String vegetarianSheet = "Vegan"+URL.replaceAll("[^0-9]", "");
			writeColumnByColumn(veganRecipeIDs, vegetarianSheet);
		}
		
		for(String URL: onePageUrlList) {
			System.out.println(URL);
			driver.navigate().to(URL);
	
			List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
			ArrayList<String> tempList = (ArrayList<String>)getURLListOnePage24(driver, URL);
			finalList.add(tempList);
			List<String> veganRecipeIDs = finalList.stream().flatMap(e->e.stream()).collect(Collectors.toList());
			String veganSheet = "Vegetarian"+URL.replaceAll("[^0-9]", "");
			writeColumnByColumn(veganRecipeIDs, veganSheet);
		}
	}

	public static void writeColumnByColumn(List<String> finalUrls, String sheetName) throws IOException {		 
		XLUtility xlutil=new XLUtility("./Data/"+sheetName+".xlsx");
		xlutil.setCellData(sheetName, 0,0, "Vegan"); // Recipe ID inplace of Vegan
		for(int r=1;r<=finalUrls.size();r++)
		{
			xlutil.setCellData(sheetName, r, 0, finalUrls.get(r-1));
		}
	}

	public static List<String> getURLListAtoZURL(WebDriver driver, String URL){  
		driver.get(URL);
		driver.manage().window().maximize();

		List<String> recipeIDs = new ArrayList<String>();
		try {
			int lastPage;
			try {
				lastPage = Integer.parseInt(driver.findElement(By.cssSelector("a.respglink:last-of-type")).getText());
			}catch(Exception e1) {
				lastPage = 1;
				e1.printStackTrace();
			}
			System.out.println("LastPage: "+URL +" " +lastPage);
			WebElement nextButtonClass = driver.findElement(By.xpath("//a[@class='rescurrpg']/following-sibling::a[1]"));
			for(int i=1; i<=lastPage; i++) {
				List<WebElement> aElements = driver.findElements(By.xpath("//span[contains(text(),'Recipe#')]"));
				System.out.println("size of urls elements : "+i+" " +URL+" "+ aElements.size());
				driver.getPageSource().contains("lunch");
				aElements.stream().parallel()
				.map(e->e.getText().replace("Recipe# ","").split(" ")[0].split("\n")[0])
				.forEach(s->recipeIDs.add(s));

				if(i==lastPage) {
					break;
				}
				// locating next button
				nextButtonClass = driver.findElement(By.xpath("//a[@class='rescurrpg']/following-sibling::a[1]"));
				//traversing through the table until the last button and adding names to the list defined about
				nextButtonClass.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(recipeIDs);
		return recipeIDs;
	}
	
	public static List<String> getURLListOnePage24(WebDriver driver, String URL){  
		driver.manage().window().maximize();

		List<String> recipeIDs = new ArrayList<String>();
		try {
				List<WebElement> aElements = driver.findElements(By.xpath("//span[contains(text(),'Recipe#')]"));
				driver.getPageSource().contains("lunch");
				aElements.stream().parallel()
				.map(e->e.getText().replace("Recipe# ","").split(" ")[0].split("\n")[0])
				.forEach(s->recipeIDs.add(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(recipeIDs);
		return recipeIDs;
	}

	
}