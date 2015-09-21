package com.msu.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.algorithms.AMultiObjectiveAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.moo.util.Random;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.packing.factory.EmptyPackingListFactory;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.TravellingSalesmanProblem;

public class OnePlusOneEA extends AMultiObjectiveAlgorithm<TravellingThiefProblem> {


	@Override
	public NonDominatedSolutionSet run(Evaluator<TravellingThiefProblem> eval) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(eval.getProblem().getMap());
		Tour<?> bestTour = LinKernighanHeuristic.getTour(new Evaluator<TravellingSalesmanProblem>(tsp));
		PackingList<?> bestList = new EmptyPackingListFactory().next(eval.getProblem());

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
