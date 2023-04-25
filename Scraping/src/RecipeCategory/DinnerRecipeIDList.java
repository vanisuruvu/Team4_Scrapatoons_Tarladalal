package RecipeCategory;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import tarladalal.XLUtility;

public class DinnerRecipeIDList {
	public static WebDriver driver;

	public static void main(String[] args) throws Exception {

		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		ops.addArguments("--headless");
		ops.setExperimentalOption("detach", true);

		WebDriver driver=new ChromeDriver(ops);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		String URL = "https://www.tarladalal.com/recipes-for-indian-dinner-939";
		driver.get(URL);

		List<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		ArrayList<String> tempList = (ArrayList<String>)getURLListAtoZURL(driver, URL);
		finalList.add(tempList);
		List<String> breakfastRecipeIDs = finalList.stream().flatMap(e->e.stream()).collect(Collectors.toList());
		writeColumnByColumn(breakfastRecipeIDs);
	}

	public static void writeColumnByColumn(List<String> finalUrls) throws IOException {		 
		XLUtility xlutil=new XLUtility("./Data/DinnerRecipeIDs.xlsx");
		xlutil.setCellData("DinnerRecipeIDs", 0,0, "Recipe ID");
		for(int r=1;r<=finalUrls.size();r++)
		{
			xlutil.setCellData("DinnerRecipeIDs", r, 0, finalUrls.get(r-1));
		}
	}

	public static List<String> getURLListAtoZURL(WebDriver driver, String URL){  
		driver.get(URL);
		driver.manage().window().maximize();

		List<String> recipeIDs = new ArrayList<String>();
		try {
			int lastPage = Integer.parseInt(driver.findElement(By.cssSelector("a.respglink:last-of-type")).getText());
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

}
