package com.msu.thief.algorithms.subproblems;

import com.msu.model.Evaluator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.Tour;

public class AlgorithmUtil {

	public static Tour calcBestTour(AbstractThiefProblem problem) {
		return new SalesmanLinKernighanHeuristic().getTour(problem, new Evaluator(Integer.MAX_VALUE));
	}


	public static Pack calcBestPackingPlan(AbstractThiefProblem problem, int maxWeight) {
		return KnapsackCombo.getPackingList(problem, new Evaluator(Integer.MAX_VALUE), maxWeight);
	}

	

}
