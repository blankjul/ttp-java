package com.msu.tsp.model.factory;

import com.msu.algorithms.LinKernighanHeuristic;
import com.msu.moo.model.Evaluator;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.model.Tour;

public class OptimumFactory<P extends ICityProblem> extends ATourFactory<P> {

	P problem = null;
	Tour<?> optimum = null;

	@Override
	public Tour<?> next(P p) {
		if (problem != p)
			problem = p;
			optimum = null;
		if (optimum == null) {
			TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(p.getMap());
			optimum = LinKernighanHeuristic.getTour(new Evaluator<ICityProblem>(tsp),p.getMaxSpeed());
		}
		return optimum;

	}

}
