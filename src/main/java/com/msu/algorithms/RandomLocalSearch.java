package com.msu.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.moo.util.Random;
import com.msu.problems.ThiefProblem;
import com.msu.problems.SalesmanProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;

public class RandomLocalSearch extends AbstractAlgorithm {

	@Override
	public NonDominatedSolutionSet run_(IEvaluator eval) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		ThiefProblem problem = (ThiefProblem) eval.getProblem();
		
		SalesmanProblem tsp = new SalesmanProblem(problem.getMap());
		Tour<?> bestTour = new SalesmanLinKernighanHeuristic().getTour(new Evaluator(tsp));
		PackingList<?> bestList = new EmptyPackingListFactory().next(problem);

		while (eval.hasNext()) {

			// copy the list
			List<Boolean> nextList = new ArrayList<>(bestList.get());
			int position = Random.getInstance().nextInt(nextList.size());

			// change the bit
			nextList.set(position, !nextList.get(position));

			Pair<Tour<?>, PackingList<?>> p = Pair.create(bestTour, new BooleanPackingList(nextList));

			Solution s = eval.evaluate(new TTPVariable(p));
			boolean isOptimal = set.add(s);

			if (isOptimal)
				bestList = new BooleanPackingList(nextList);
		}

		return set;
	}

}
