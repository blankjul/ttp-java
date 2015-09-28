package com.msu.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.PackingList;
import com.msu.knp.model.factory.EmptyPackingListFactory;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.moo.util.Random;
import com.msu.thief.ThiefProblem;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.model.Tour;

public class OnePlusOneEA extends AbstractAlgorithm {


	Solution best = null;
	
	@Override
	public NonDominatedSolutionSet run(IEvaluator eval) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		ThiefProblem problem =(ThiefProblem) eval.getProblem();
		
		SymmetricMap map = problem.getMap();
		map.multipleCosts(problem.getMaxSpeed());
		
		TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(map);
		
		Tour<?> bestTour = LinKernighanHeuristic.getTour(new Evaluator(tsp), problem.getMaxSpeed());
		PackingList<?> bestList = new EmptyPackingListFactory().next(problem);

		while (eval.hasNext()) {

			List<Boolean> nextList = new ArrayList<>(bestList.get());
			for (int i = 0; i < nextList.size(); i++) {
				if (Random.getInstance().nextDouble() < 1d / nextList.size()) {
					nextList.set(i, !nextList.get(i));
				}
			}
			
			Pair<Tour<?>, PackingList<?>> p = Pair.create(bestTour, new BooleanPackingList(nextList));
			Solution s = eval.evaluate(new TTPVariable(p));
			boolean isOptimal = set.add(s);

			if (isOptimal)
				bestList = new BooleanPackingList(nextList);
		}

		return set;
	}

}
