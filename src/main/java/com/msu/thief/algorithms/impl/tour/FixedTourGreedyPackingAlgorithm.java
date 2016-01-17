package com.msu.thief.algorithms.impl.tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Pair;
import com.msu.moo.util.Util;
import com.msu.thief.algorithms.ItemHeuristicUtil;
import com.msu.thief.algorithms.interfaces.AFixedTourSingleObjectiveAlgorithm;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;

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
public class FixedTourGreedyPackingAlgorithm extends AFixedTourSingleObjectiveAlgorithm {

	@Override
	public Solution<Pack> run(ThiefProblemWithFixedTour problem, IEvaluator evaluator, MyRandom rand) {

		// empty packing list
		Pack pack = new Pack();

		// last best known fitness
		double lastFitness = evaluator.evaluate(problem, pack).getObjective(0);

		// contains all indices which gave an improvement on the last set
		Set<Integer> nextItems = new HashSet<>(Util.createIndex(0, problem.getProblem().numOfItems()));

		while (true) {

			if (!evaluator.hasNext())
				break;

			// calculate all objectives values when one item is added
			Map<Integer, Solution<Pack>> next = ItemHeuristicUtil.calcObjectiveWhenAdded(problem, evaluator, pack, nextItems);

			// create a list with all items that improve the current pack
			nextItems.clear();
			List<Pair<Integer, Solution<Pack>>> nextIndices = new ArrayList<>();
			for (Entry<Integer, Solution<Pack>> entry : next.entrySet()) {
				if (!entry.getValue().hasConstrainViolations() && entry.getValue().getObjective(0) < lastFitness) {
					nextItems.add(entry.getKey());
					nextIndices.add(Pair.create(entry.getKey(), entry.getValue()));
				}
			}

			// no item improves the current value
			if (nextIndices.isEmpty())
				break;

			pack.add(selectBest(nextIndices));

		}

		return evaluator.evaluate(problem, pack);
	}

	public static Integer selectBest(List<Pair<Integer, Solution<Pack>>> nextIndices) {
		Collections.sort(nextIndices, (p1, p2) -> p1.second.getObjective(0).compareTo(p2.second.getObjective(0)));
		final int nextItemIdx = nextIndices.get(0).first;
		return nextItemIdx;
	}

}
