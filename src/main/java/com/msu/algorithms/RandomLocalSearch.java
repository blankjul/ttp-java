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
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.model.Tour;

public class RandomLocalSearch extends AbstractAlgorithm {

	@Override
	public NonDominatedSolutionSet run(IEvaluator eval) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		ThiefProblem problem = (ThiefProblem) eval.getProblem();
		
		TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(problem.getMap());
		Tour<?> bestTour = new LinKernighanHeuristic().getTour(new Evaluator(tsp), problem.getMaxSpeed());
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
