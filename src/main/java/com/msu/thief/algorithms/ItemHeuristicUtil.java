package com.msu.thief.algorithms;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.ISolution;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;

/**
 * This class provides some useful function to estimate if an item is good or
 * not.
 *
 */
public class ItemHeuristicUtil {

	
	/**
	 * @return the objective value when one item is removed from the pack
	 */
	public static 
		Map<Integer, ISolution<Pack>> calcObjectiveRemoved(ThiefProblemWithFixedTour problem, IEvaluator eval,
			Pack pack) {
		Map<Integer, ISolution<Pack>> hash = new HashMap<>();
		for (Integer idx : new HashSet<>(pack.decode())) {
			hash.put(idx, evaluateWhenBitfipped(problem, eval, pack, idx));
		}
		return hash;
	}

	/**
	 * @param itemsToAdd all items that should be considered to add
	 * @return the heuristic value of all items when they are added.
	 */
	public static Map<Integer, ISolution<Pack>> calcObjectiveWhenAdded(ThiefProblemWithFixedTour problem, IEvaluator eval,
			Pack pack, Collection<Integer> itemsToAdd) {

		Map<Integer, ISolution<Pack>> hash = new HashMap<>();
		for (Integer idx : itemsToAdd) {
			if (pack.isPicked(idx))
				throw new RuntimeException(
						String.format("Item %s can not be added because it is part of the knapsack.", idx));
			hash.put(idx, evaluateWhenBitfipped(problem, eval, pack, idx));
		}
		return hash;
	}

	/**
	 * @return the solution if the bit is fliped. If item is picked, it is
	 *         removed. If it is not present it is added.
	 */
	public static ISolution<Pack> evaluateWhenBitfipped(ThiefProblemWithFixedTour problem, IEvaluator eval, Pack p,
			int idx) {

		ISolution<Pack> s = null;
		
		Pack pack = p.copy();

		if (pack.isPicked(idx)) {
			pack.remove(idx);
			s = eval.evaluate(problem, pack);
		} else {
			pack.add(idx);
			s = eval.evaluate(problem, pack);
		}

		return s;
	}

}
