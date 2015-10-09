package com.msu.thief.variable.tour.factory;

import com.msu.algorithms.SalesmanLinKernighanHeuristic;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.util.Random;
import com.msu.problems.ICityProblem;
import com.msu.problems.SalesmanProblem;
import com.msu.thief.variable.tour.Tour;

public class OptimumFactory extends ATourFactory {

	IProblem problem = null;
	Tour<?> optimum = null;

	@Override
	public Tour<?> next(IProblem p) {
		if (problem != p)
			problem = p;
			optimum = null;
		if (optimum == null) {
			ICityProblem cityProblem = (ICityProblem) p;
			SalesmanProblem tsp = new SalesmanProblem(cityProblem.getMap());
			optimum = new SalesmanLinKernighanHeuristic().getTour(new Evaluator(tsp));
		}
		
		if (Random.getInstance().nextDouble() < 0.5) {
			return optimum;
		} else {
			return optimum.getSymmetric();
		}
		

	}

}
