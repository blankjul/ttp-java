package com.msu.tsp.model.factory;

import java.util.List;

import com.msu.algorithms.LinKernighanHeuristic;
import com.msu.moo.model.Evaluator;
import com.msu.moo.util.Random;
import com.msu.moo.util.Util;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.model.StandardTour;
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
			optimum = LinKernighanHeuristic.getTour(new Evaluator<TravellingSalesmanProblem>(tsp));
		}
		double rnd = Random.getInstance().nextDouble(); 
		if (rnd <= 1)
			return optimum;
		else if (rnd < 0.6) {
			return new RandomFactory<>().next(problem);
		}
		else {
			List<Integer> l = optimum.encode();
			for (int i = 0; i < (int) (l.size() / 10.0); i++) {
				int a = Random.getInstance().nextInt(0, l.size() - 1);
				int b = Random.getInstance().nextInt(0, l.size() - 1);
				Util.swap(l, a, b);
			}
			return new StandardTour(l);
		}

	}

}
