package com.msu.thief.algorithms.bilevel.tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmSingleObjectiveUtil;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.util.MyRandom;
import com.msu.util.Pair;
import com.msu.util.Util;

/**
 * Greedy algorithms starts with an empty knapsack and starting to add items
 * according to a specific heuristic.
 * 
 * TYPE.BEST Always an item with the largest improvement (and feasible) is taken
 * -> deterministic
 * 
 * TYPE.RANDOM Take randomly one item which brings an improvement (and
 * feasible).
 * 
 */
public class GreedyPackingAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour> {

	public static enum TYPE {
		BEST, RANDOM
	};

	// ! type of algorithm which needs to be defined by constructor
	protected TYPE type = TYPE.BEST;

	public GreedyPackingAlgorithm() {
		super();
	}

	public GreedyPackingAlgorithm(TYPE type) {
		super();
		this.type = type;
	}

	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		while (eval.hasNext()) {

			// empty packing list
			IntegerSetPackingList pack = new IntegerSetPackingList(problem.numOfItems());

			// last best known fitness
			double lastFitness = eval.evaluate(problem, pack).getObjectives(0);

			// contains all indices which gave an improvement on the last set
			Set<Integer> nextItems = new HashSet<>(Util.createIndex(0, problem.numOfItems()));

			while (true) {
				
				if (!eval.hasNext()) break;
				
				// calculate all objectives values when one item is added
				Map<Integer, Solution> next = AlgorithmSingleObjectiveUtil.calcObjectiveWhenAdded(problem, eval, pack,
						nextItems);

				// create a list with all items that improve the current pack
				nextItems.clear();
				List<Pair<Integer, Solution>> nextIndices = new ArrayList<>();
				for (Entry<Integer, Solution> entry : next.entrySet()) {
					if (!entry.getValue().hasConstrainViolations() && entry.getValue().getObjectives(0) < lastFitness) {
						nextItems.add(entry.getKey());
						nextIndices.add(Pair.create(entry.getKey(), entry.getValue()));
					}
				}

				if (nextIndices.isEmpty())
					break;

				// select next based on the type
				int nextItemIdx = -1;
				if (type == TYPE.BEST)
					nextItemIdx = selectBest(nextIndices);
				else if (type == TYPE.RANDOM)
					nextItemIdx = selectRandom(nextIndices, rand);
				else
					throw new RuntimeException("Unknown type of algorithm.");

				Set<Integer> b = new HashSet<>(pack.toIndexSet());
				b.add(nextItemIdx);
				nextItems.remove(nextItemIdx);
				pack = new IntegerSetPackingList(b, problem.numOfItems());
			}

			Solution s = eval.evaluate(problem, pack);
			set.add(s);

			// this is deterministic so no more iteration are needed
			if (type == TYPE.BEST)
				break;

		}

		return set.get(0);
	}

	protected static Integer selectBest(List<Pair<Integer, Solution>> nextIndices) {
		Collections.sort(nextIndices, (p1, p2) -> p1.second.getObjectives(0).compareTo(p2.second.getObjectives(0)));
		final int nextItemIdx = nextIndices.get(0).first;
		return nextItemIdx;
	}

	protected static Integer selectRandom(List<Pair<Integer, Solution>> nextIndices, MyRandom rand) {
		return rand.select(nextIndices).first;
	}

}
