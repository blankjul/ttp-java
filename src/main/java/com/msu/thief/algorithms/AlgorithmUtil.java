package com.msu.thief.algorithms;

import com.msu.model.Evaluator;
import com.msu.thief.problems.KnapsackProblem;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;

public class AlgorithmUtil {

	public static Tour<?> calcBestTour(AbstractThiefProblem problem) {
		SalesmanProblem tsp = new SalesmanProblem(problem.getMap());
		return calcBestTour(tsp);
	}

	public static Tour<?> calcBestTour(SalesmanProblem problem) {
		Tour<?> bestTour = new SalesmanLinKernighanHeuristic().getTour(problem, new Evaluator(Integer.MAX_VALUE));
		return bestTour;
	}

	public static PackingList<?> calcBestPackingPlan(AbstractThiefProblem problem) {
		KnapsackProblem knp = new KnapsackProblem((int) problem.getMaxWeight(), problem.getItems());
		return calcBestPackingPlan(knp);
	}

	public static PackingList<?> calcBestPackingPlan(KnapsackProblem problem) {
		PackingList<?> bestPackingPlan = KnapsackCombo.getPackingList(problem, new Evaluator(Integer.MAX_VALUE));
		return bestPackingPlan;
	}

	

}
