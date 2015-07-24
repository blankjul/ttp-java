package com.moo.ttp.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.moo.ttp.problems.TravellingThiefProblem;

public class RepairPickingPlan {

	public static void repair(TravellingThiefProblem ttp, Boolean[] b) {
		
		// get the current weight
		
		/*
		int weight = ttp.getWeightWithoutDropping(b);

		List<Integer> indices = collectItems(b, true);
		Collections.shuffle(indices);
		for (int index : indices) {
			b[index] = false;
			weight -= ttp.getItems().get(index).getWeight();
			if (weight <= ttp.getMaxWeight()) break;
		}
		*/

	}
	
	private static List<Integer> collectItems(Boolean[] b, boolean value) {
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < b.length; i++) {
			if (b[i] == value) indices.add(i);
		}
		return indices;
	}
	
}
