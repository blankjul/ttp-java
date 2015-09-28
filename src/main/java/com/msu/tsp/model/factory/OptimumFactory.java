package com.msu.tsp.model.factory;

import com.msu.algorithms.LinKernighanHeuristic;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.model.Tour;

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
			TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(cityProblem.getMap());
			optimum = LinKernighanHeuristic.getTour(new Evaluator(tsp),cityProblem.getMaxSpeed());
		}
		return optimum;

	}

}
