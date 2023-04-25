package RecipeCategory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import tarladalal.XLUtility;

public class UpdateRecipeCategory {
	public static void main(String[] args) throws IOException {
		List<String> breakfastList = XLUtility.getData("BreakfastRecipeIDs.xlsx","BreakfastRecipeIDs");
		List<String> lunchList = XLUtility.getData("LunchRecipeIDs.xlsx","LunchRecipeIDs");
		List<String> dinnerList = XLUtility.getData("DinnerMenuRecipeIDs.xlsx","DinnerRecipeIDs");
		List<String> dinnerMenuList = XLUtility.getData("DinnerMenuRecipeIDs.xlsx","DinnerMenuRecipeIDs");
		List<String> snackList = XLUtility.getData("SnackStarterRecipeIDs.xlsx","SnackStarterIDs");

		
		XLUtility xlutil=new XLUtility("./dataFiles/tarladalal_recipe.xlsx");
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
		
		List<String> recipeIDList = XLUtility.getRecipeDetailsData("./dataFiles/tarladalal.xlsx", "Sheet1", "Recipe ID");
//		List<String> ingredientsList = XLUtility.getIngredientsData("./dataFiles/tarladalal.xlsx", "Sheet1");
		
		try {
			IntStream.range(1, recipeIDList.size()+1)  //.parallel()
				.forEach(r -> {
					try {
					//read data from web table
					List<String> recipeCategory= new ArrayList<String>();
					String recipeCatString = "";
					String recipeID = recipeIDList.get(r-1);
					if(breakfastList.contains(recipeID)){
						recipeCategory.add("Breakfast");
					}
					if(lunchList.contains(recipeID)){
						recipeCategory.add("Lunch");
					}
					if(dinnerList.contains(recipeID) || dinnerMenuList.contains(recipeID)){
						recipeCategory.add("Dinner");
					}
					if(snackList.contains(recipeID)){
						recipeCategory.add("Snack");
					}
					recipeCatString = recipeCategory.toString();
					System.out.println(recipeID + ": "+ recipeCatString);
					//writing the data in excel sheet
					xlutil.setCellData("Sheet1", r, 2, recipeCatString);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Recipe Category updated succesfully.....");
	}
}
