package tarladalal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import EnumDataObjects.FoodCategory;
import EnumDataObjects.RecipeCategory;


//record Recipe (int recipeID) {}
public class RecipeTest {
	static List<String> eliminatedList = Arrays.asList("Egg","Milk");
	public static void main(String[] args) {
		List<String> ingredientsList = Arrays.asList("chopped cabbage", "egg slice", "whole wheat flour", "chopped green chilles");
		RecipeCategory[] recipeCat = {RecipeCategory.Breakfast, RecipeCategory.Lunch};
		FoodCategory[] foodCat = {FoodCategory.Jain, FoodCategory.Vegan};
		HashMap<String, String> nutrientValuesCabbage = new HashMap<String,String>();
		nutrientValuesCabbage.put("Energy", "109 cal");
		
		
		String input = "[grated raw mango, kabuli chana (white chick peas), turmeric powder (haldi), salt, fenugreek (methi) seeds, fenugreek seeds (methi) powder, fennel seeds (saunf), asafoetida (hing), nigella seeds (kalonji), whole dry kashmiri red chillies, chilli powder, mustard (rai / sarson) oil]";
		String output = input.replace("[", "").replace("]", "").replace(", ", "\n");
		System.out.println(output);
	/*	Recipe cabbageParatha = new Recipe(1, 
				"Cabbage Paratha",
				recipeCat, 
				foodCat,
				ingredientsList,
				"20 minutes", 
				"40 minutes", 
				"Prep cabbage method",
				nutrientValuesCabbage,
				TargettedMorbidConditions.Diabetes,
				"abcdURL");

		boolean isIncluded = !cabbageParatha.ingredients().stream()
		.anyMatch(y-> eliminatedList.stream().anyMatch(x->StringUtils.containsIgnoreCase(y,x)));
		System.out.println(isIncluded);
		*/
	}

/*	public static String getResultfromExcel(String sheetname, int rownumber) throws InvalidFormatException, IOException {
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testdata = reader.getData("./Data/IngredientsAndComorbidities-ScrapperHackathon.xlsx", 
				"Diabetes-Hypothyroidism-Hyperte");
		String result = testdata.get(rownumber).get("Result");
		return result;
	} */
}
