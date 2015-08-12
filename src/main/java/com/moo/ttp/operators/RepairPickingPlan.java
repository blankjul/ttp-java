package com.moo.ttp.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.moo.ttp.model.Item;
import com.moo.ttp.problems.Knapsack;
import com.moo.ttp.problems.ttp.TravellingThiefProblem;

public class RepairPickingPlan {

	public static void repair(TravellingThiefProblem ttp, Boolean[] b) {
		
		List<Item> items = ttp.getSettings().getItems().getItems();
		int weight = Knapsack.getWeight(items, b);

		List<Integer> indices = collectItems(b, true);
		Collections.shuffle(indices);
		for (int index : indices) {
			b[index] = false;
			weight -= items.get(index).getWeight();
			if (weight <= ttp.getSettings().getMaxWeight()) break;
		}
	}
	
	private static List<Integer> collectItems(Boolean[] b, boolean value) {
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < b.length; i++) {
			if (b[i] == value) indices.add(i);
		}
		return indices;
	}
	
	
}
