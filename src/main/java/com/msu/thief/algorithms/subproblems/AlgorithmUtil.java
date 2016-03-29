package com.msu.thief.algorithms.subproblems;

import java.util.List;

import com.msu.thief.model.Item;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.Tour;

public class AlgorithmUtil {

	public static Tour calcBestTour(AbstractThiefProblem problem) {
		return new SalesmanLinKernighanHeuristic().getTour(problem);
	}


	public static Pack calcBestPackingPlan(List<Item> items, int maxWeight) {
		return KnapsackCombo.getPackingList(items, maxWeight);
	}
	

}
