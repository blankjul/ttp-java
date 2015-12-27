package com.msu.thief.variable;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.KnapsackLocalSearch;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class RecombinationUtil {

	protected static KnapsackLocalSearch localSearch = new Builder<KnapsackLocalSearch>(
			KnapsackLocalSearch.class)
			.set("populationSize", 20)
			.set("probMutation", 0.3)
			.set("factory", new OptimalPackingListFactory())
			.build();
	
	public static IVariable localSearch(IProblem problem,  Tour<?> tour, MyRandom rand, IEvaluator eval, int evaluations) {
		
		IEvaluator local = eval.createChildEvaluator(evaluations);
		
		ThiefProblemWithFixedTour fixedTour = new ThiefProblemWithFixedTour((AbstractThiefProblem)problem,(Tour<?>) tour);
		
		//Solution s = new OnePlusOneEAFixedTour().run__(fixedTour, local, rand);
		Solution s = localSearch.run__(fixedTour, local, rand);
		
		
		return s.getVariable();
		
	}
	
}
