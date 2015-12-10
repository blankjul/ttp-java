package com.msu.thief.algorithms;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.thief.variable.pack.PackingList;

public class AlgorithmSingleObjectiveUtil {
	

	public static Map<Integer, Solution> calcObjectiveRemoved(ThiefProblemWithFixedTour problem, IEvaluator eval,
			PackingList<?> pack) {
		Map<Integer, Solution> hash = new HashMap<>();
		for (Integer idx : pack.toIndexSet()) {
			hash.put(idx, evaluateWhenBitfipped(problem, eval, pack, idx));
		}
		return hash;
	}
	
	
	public static Map<Integer, Solution> calcObjectiveWhenAdded(ThiefProblemWithFixedTour problem, IEvaluator eval,
			PackingList<?> pack, Collection<Integer> itemsToAdd) {

		Map<Integer, Solution> hash = new HashMap<>();
		for (Integer idx : itemsToAdd) {
			if (pack.isPicked(idx)) 
				throw new RuntimeException(String.format("Item %s can not be added because it is part of the knapsack.", idx));
			if (!eval.hasNext()) break;
			hash.put(idx, evaluateWhenBitfipped(problem, eval, pack, idx));
		}
		return hash;
	}

	public static Solution evaluateWhenBitfipped(ThiefProblemWithFixedTour problem, 
			IEvaluator eval, PackingList<?> pack, int idx) {
		
		Set<Integer> itemsToEvaluate = new HashSet<>(pack.toIndexSet());
		if (itemsToEvaluate.contains(idx)) itemsToEvaluate.remove(idx);
		else itemsToEvaluate.add(idx);
		
		IntegerSetPackingList b = new IntegerSetPackingList(itemsToEvaluate, problem.numOfItems());
		Solution s = eval.evaluate(problem, b);
		return s;
	}

}
