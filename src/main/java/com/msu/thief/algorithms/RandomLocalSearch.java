package com.msu.thief.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.AbstractAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;
import com.msu.util.MyRandom;

public class RandomLocalSearch extends AbstractAlgorithm {

	@Override
	public NonDominatedSolutionSet run_(IProblem p, IEvaluator eval, MyRandom rand) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		ThiefProblem problem = (ThiefProblem) p;
		
		SalesmanProblem tsp = new SalesmanProblem(problem.getMap());
		Tour<?> bestTour = new SalesmanLinKernighanHeuristic().getTour(tsp ,new Evaluator(Integer.MAX_VALUE));
		PackingList<?> bestList = new EmptyPackingListFactory().next(problem, rand);

		while (eval.hasNext()) {

			// copy the list
			List<Boolean> nextList = new ArrayList<>(bestList.get());
			int position = rand.nextInt(nextList.size());

			// change the bit
			nextList.set(position, !nextList.get(position));

			Pair<Tour<?>, PackingList<?>> pair = Pair.create(bestTour, new BooleanPackingList(nextList));

			Solution s = eval.evaluate(problem, new TTPVariable(pair));
			boolean isOptimal = set.add(s);

			if (isOptimal)
				bestList = new BooleanPackingList(nextList);
		}

		return set;
	}

}
