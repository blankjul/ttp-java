package com.msu.thief.variable.tour.factory;

import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.thief.algorithms.SalesmanLinKernighanHeuristic;
import com.msu.thief.problems.ICityProblem;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Random;

public class OptimalTourFactory extends ATourFactory {

	IProblem problem = null;
	Tour<?> optimum = null;

	@Override
	public Tour<?> next_(IProblem p, Random rand) {
		if (problem != p)
			problem = p;
			optimum = null;
		if (optimum == null) {
			ICityProblem cityProblem = (ICityProblem) p;
			SalesmanProblem tsp = new SalesmanProblem(cityProblem.getMap());
			optimum = new SalesmanLinKernighanHeuristic().getTour(tsp, new Evaluator(Integer.MAX_VALUE));
		}
		
		if (rand.nextDouble() < 0.5) {
			return optimum;
		} else {
			return optimum.getSymmetric();
		}
		

	}

}
