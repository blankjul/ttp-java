package com.msu.algorithms;

import com.msu.model.Evaluator;
import com.msu.problems.KnapsackProblem;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;

public class AlgorithmUtil {
	
	
	public static Tour<?> calcBestTour(ThiefProblem problem) {
		SalesmanProblem tsp = new SalesmanProblem(problem.getMap());
		return calcBestTour(tsp);
	}
	
	public static Tour<?> calcBestTour(SalesmanProblem problem) {
		Tour<?> bestTour = new SalesmanLinKernighanHeuristic().getTour(problem, new Evaluator(Integer.MAX_VALUE));
		return bestTour;
	}
	
	public static PackingList<?> calcBestPackingPlan(ThiefProblem problem, double maxWeightPerc) {
		KnapsackProblem knp = new KnapsackProblem((int) problem.getMaxWeight(), problem.getItems());
		return calcBestPackingPlan(knp, maxWeightPerc);
	}
	
	public static PackingList<?> calcBestPackingPlan(KnapsackProblem problem, double maxWeightPerc) {
		PackingList<?> bestPackingPlan = KnapsackCombo.getPackingList(problem, new Evaluator(Integer.MAX_VALUE));
		return bestPackingPlan;
	}

}
