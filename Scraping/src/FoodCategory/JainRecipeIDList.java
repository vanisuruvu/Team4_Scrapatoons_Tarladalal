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

public class JainRecipeIDList {

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
				"https://www.tarladalal.com/recipes-for-veg-recipes-jain-34",
				"https://www.tarladalal.com/recipes-for-jain-soups-35",
				"https://www.tarladalal.com/recipes-for-jain-naashta-36",
				"https://www.tarladalal.com/recipes-for-jain-rotis-37",
				"https://www.tarladalal.com/recipes-for-jain-sabzi-gravies-39",
				"https://www.tarladalal.com/recipes-for-jain-dal-kadhi-40",
				"https://www.tarladalal.com/recipes-for-jain-pickles-chutneys-raita-salad-41",
				"https://www.tarladalal.com/recipes-for-jain-international-42",
				"https://www.tarladalal.com/recipes-for-jain-paryushan-43",
				"https://www.tarladalal.com/recipes-for-breakfast-jain-breakfast-159",
				"https://www.tarladalal.com/recipes-for-salads-jain-salads-173",
				"https://www.tarladalal.com/recipes-for-starters-snacks-jain-snacks-193",
				"https://www.tarladalal.com/recipes-for-main-subzis-curries-jain-subzis-216"
				);
		List<String> onePageUrlList = Arrays.asList(
				"https://www.tarladalal.com/recipes-for-jain-rice-38",
				"https://www.tarladalal.com/recipes-for-chunky-creamy-jain-soup-165",
				"https://www.tarladalal.com/recipes-for-main-rice-delicacies-jain-rice-596",
				"https://www.tarladalal.com/recipes-using-jain-tomato-ketchup-2023"
				);
		for(String URL: urlList) {
			System.out.println(URL);
			driver.navigate().to(URL);
	
			List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
			ArrayList<String> tempList = (ArrayList<String>)getURLListAtoZURL(driver, URL);
			finalList.add(tempList);
			List<String> jainRecipeIDs = finalList.stream().flatMap(e->e.stream()).collect(Collectors.toList());
			String jainSheet = "Jain"+URL.replaceAll("[^0-9]", "");
			writeColumnByColumn(jainRecipeIDs, jainSheet);
		}
		
		for(String URL: onePageUrlList) {
			System.out.println(URL);
			driver.navigate().to(URL);
	
			List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
			ArrayList<String> tempList = (ArrayList<String>)getURLListOnePage24(driver, URL);
			finalList.add(tempList);
			List<String> jainRecipeIDs = finalList.stream().flatMap(e->e.stream()).collect(Collectors.toList());
			String jainSheet = "Jain"+URL.replaceAll("[^0-9]", "");
			writeColumnByColumn(jainRecipeIDs, jainSheet);
		}
	}

	public static void writeColumnByColumn(List<String> finalUrls, String sheetName) throws IOException {		 
		XLUtility xlutil=new XLUtility("./Data/Jain.xlsx");
		xlutil.setCellData(sheetName, 0,0, "Jain"); // Recipe ID inplace of Jain
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