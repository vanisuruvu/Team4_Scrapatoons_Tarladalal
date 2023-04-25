package tarladalal;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GetDistinctIngredients {
	public static void main(String[] args) throws IOException {
		String destinationPath = "./dataFiles/tarladalal.xlsx";
		
		List<String> ingredientsList = XLUtility.getRecipeDetailsData(destinationPath, "Sheet1", "Ingredients");
//		List<String> distinctIngredientList = 
				ingredientsList.stream()
							.filter(e->!e.contains("[]"))
							.map(e->e.replace("[", "").replace("]", ""))
							.map(e->Arrays.asList(e.split(", ")))
							.flatMap(e->e.stream())
//							.peek(System.out::println)
							.map(e->e.trim())
							.distinct()
							.sorted()
							.forEach(System.out::println);
//							.collect(Collectors.toList());
//		System.out.println(distinctIngredientList);

	}
}
