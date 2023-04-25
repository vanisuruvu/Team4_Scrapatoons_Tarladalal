package FoodCategory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import tarladalal.XLUtility;

public class UpdateFoodCategory {
	public static void main(String[] args) throws IOException {
		List<String> veganList = XLUtility.getData("Vegan.xlsx","Vegan");
		System.out.println(veganList);
		List<String> vegetarianList = XLUtility.getData("Vegetarian.xlsx","Vegetarian");
		System.out.println(vegetarianList);
		List<String> jainList = XLUtility.getData("Jain.xlsx","Jain");
		
		XLUtility xlutil=new XLUtility(".\\dataFiles\\tarladalal_food.xlsx");
		
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
		System.out.println("HERE: "+recipeIDList.get(300));
		List<String> ingredientsList = XLUtility.getRecipeDetailsData("./dataFiles/tarladalal.xlsx", "Sheet1","Ingredients");
		System.out.println("HERE: "+ingredientsList.get(300));
		
		try {
			IntStream.range(1, recipeIDList.size()+1)  //.parallel()
				.forEach(r -> {
					try {
					//read data from web table
					List<String> foodCategory= new ArrayList<String>();
					String foodCatString = "";
					String recipeID = recipeIDList.get(r-1);
					if(veganList.contains(recipeID)){
						foodCategory.add("Vegan");
					}
					if(vegetarianList.contains(recipeID)){
						foodCategory.add("Vegetarian");
					}
					if(jainList.contains(recipeID)){
						foodCategory.add("Jain");
					}
					if(ingredientsList.contains("egg") || ingredientsList.contains("Egg")
							|| ingredientsList.contains("eggs") || ingredientsList.contains("Eggs")) {
						foodCategory.add("Eggitarian");
					}
					foodCatString = foodCategory.toString();
					System.out.println(foodCatString);
					//writing the data in excel sheet
					xlutil.setCellData("Sheet1", r, 3, foodCatString);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Food Category updated succesfully.....");
	}
}
