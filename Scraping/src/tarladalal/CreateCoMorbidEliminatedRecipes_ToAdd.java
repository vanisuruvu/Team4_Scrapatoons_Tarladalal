package tarladalal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class CreateCoMorbidEliminatedRecipes_ToAdd {
	public static void main(String[] args) throws IOException {
		String eliminated_toAdd_excel = "EliminatedList_ToAdd.xlsx";
		List<String> diabetesElimList = XLUtility.getData(eliminated_toAdd_excel,"Diabetes");
		List<String> hypoThyroidismList = XLUtility.getData(eliminated_toAdd_excel,"Hypothyroidism");
		List<String> hypertensionList = XLUtility.getData(eliminated_toAdd_excel,"Hypertension");
		List<String> pcosList = XLUtility.getData(eliminated_toAdd_excel,"PCOS");

		List<String> diabetesToAddList = XLUtility.getData(eliminated_toAdd_excel,"ToAdd_Diabetes");
		List<String> hypertensionToAddList = XLUtility.getData(eliminated_toAdd_excel,"ToAdd_Hypertension");
		List<String> hypothyroidismToAddList = XLUtility.getData(eliminated_toAdd_excel,"ToAdd_Hypothyroidism");
		List<String> pcosToAddList = XLUtility.getData(eliminated_toAdd_excel,"ToAdd_PCOS");

		String destinationPath = "./dataFiles/tarladalal.xlsx";
		
		List<String> recipeIDList = XLUtility.getRecipeDetailsData(destinationPath, "Sheet1", "Recipe ID");
		List<String> ingredientsList = XLUtility.getRecipeDetailsData(destinationPath, "Sheet1", "Ingredients");

		String path=".\\dataFiles\\comorbidCoditions.xlsx";
		XLUtility xlUtil=new XLUtility(destinationPath);

		//Write headers in excel sheet
		xlUtil.setCellData("Sheet1", 0,0, "Recipe ID");
		xlUtil.setCellData("Sheet1", 0,1, "Recipe Name");
		xlUtil.setCellData("Sheet1", 0,2, "Recipe Category(Breakfast/lunch/snack/dinner)");
		xlUtil.setCellData("Sheet1", 0,3, "Food Category(Veg/non-veg/vegan/Jain)");
		xlUtil.setCellData("Sheet1", 0,4, "Ingredients");
		xlUtil.setCellData("Sheet1", 0,5, "Preparation Time");
		xlUtil.setCellData("Sheet1", 0,6, "Cooking Time");
		xlUtil.setCellData("Sheet1", 0,7, "Preparation method");
		xlUtil.setCellData("Sheet1", 0,8, "Nutrient values");
		xlUtil.setCellData("Sheet1", 0,9, "Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		xlUtil.setCellData("Sheet1", 0,10, "Recipe URL");

		try {
			IntStream.range(1, recipeIDList.size()+1)  //.parallel()
			.forEach(r -> {
				try {
					System.out.println(recipeIDList.get(r-1));
					List<String> coMorbidList = new ArrayList<String> ();
					List<String> toAddList = new ArrayList<String> ();
					List<String> ingredients = Arrays.asList(ingredientsList.get(r-1));
					
					// Diabetes
					boolean isIncludedDiabetes = !ingredients.stream()
							.anyMatch(y-> diabetesElimList.stream().anyMatch(x->StringUtils.containsIgnoreCase(y.replace(" ", ""),x.replace(" ", ""))));
					if(isIncludedDiabetes) {
						coMorbidList.add("Diabetes");
						boolean isDiabetesToAddPresent = ingredients.stream()
								.anyMatch(y-> diabetesToAddList.stream().anyMatch(x->StringUtils.containsIgnoreCase(y.replace(" ", ""),x.replace(" ", ""))));
						if(isDiabetesToAddPresent) {
							//writing the data in excel sheet
							toAddList.add("DiabetetesToAddPresent");
						}
					}
					
					// Hypothyroidism
					boolean isIncludedHypothyroidism = !ingredients.stream()
							.anyMatch(y-> hypoThyroidismList.stream().anyMatch(x->StringUtils.containsIgnoreCase(y.replace(" ", ""),x.replace(" ", ""))));
					
					if(isIncludedHypothyroidism) {
						coMorbidList.add("Hypothyroidism");
						boolean isHypertensionToAddPresent = ingredients.stream()
								.anyMatch(y-> hypertensionToAddList.stream().anyMatch(x->StringUtils.containsIgnoreCase(y.replace(" ", ""),x.replace(" ", ""))));
						if(isHypertensionToAddPresent) {
							//writing the data in excel sheet
							toAddList.add("HypertensionToAddPresent");
						}
					}
					
					// Hypertension
					boolean isIncludedHypertension = !ingredients.stream()
							.anyMatch(y-> hypertensionList.stream().anyMatch(x->StringUtils.containsIgnoreCase(y.replace(" ", ""),x.replace(" ", ""))));
					
					if(isIncludedHypertension) {
						coMorbidList.add("Hypertension");
						boolean isHypothyroidismToAddPresent = ingredients.stream()
								.anyMatch(y-> hypothyroidismToAddList.stream().anyMatch(x->StringUtils.containsIgnoreCase(y.replace(" ", ""),x.replace(" ", ""))));
						if(isHypothyroidismToAddPresent) {
							//writing the data in excel sheet
							toAddList.add("HypothyroidismToAddPresent");
						}
					}
					
					// Hypertension
					boolean isIncludedPCOS = !ingredients.stream()
							.anyMatch(y-> pcosList.stream().anyMatch(x->StringUtils.containsIgnoreCase(y.replace(" ", ""),x.replace(" ", ""))));
					
					if(isIncludedPCOS) {
						coMorbidList.add("PCOS");
						boolean isPCOSToAddPresent = ingredients.stream()
								.anyMatch(y-> pcosToAddList.stream()
										.anyMatch(x->StringUtils.containsIgnoreCase(y.replace(" ", ""),x.replace(" ", ""))));
						if(isPCOSToAddPresent) {
							//writing the data in excel sheet
							toAddList.add("PCOSToAddPresent");
						}
					}
					
					String coMorbidConditionString = coMorbidList.toString();
					String toAddPresentString = toAddList.toString();
						//writing the data in excel sheet
//						xlUtil.setCellData("Sheet1", r, 0, recipeIDList.get(r-1));
//						xlUtil.setCellData("Sheet1", r, 1, recipeNameList.get(r-1));
//						xlUtil.setCellData("Sheet1", r, 2, recipeCatetogryList.get(r-1));
//						xlUtil.setCellData("Sheet1", r, 3, foodCatetogryList.get(r-1));
//						xlUtil.setCellData("Sheet1", r, 4, recipeCatetogryList.get(r-1));
//						xlUtil.setCellData("Sheet1", r, 5, preparationTimeList.get(r-1));
//						xlUtil.setCellData("Sheet1", r, 6, cookingTimeList.get(r-1));
//						xlUtil.setCellData("Sheet1", r, 7, preparationMethodList.get(r-1));
//						xlUtil.setCellData("Sheet1", r, 8, nutrientValuesList.get(r-1));
						xlUtil.setCellData("Sheet1", r, 9, coMorbidConditionString);
//						xlUtil.setCellData("Sheet1", r, 10, recipeURLList.get(r-1));
						xlUtil.setCellData("Sheet1", r, 11, toAddPresentString);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("CoMorbid Conditions with removing eliminated list, toAdd list created succesfully.....");
	}
}
