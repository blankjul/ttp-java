package com.msu.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.KnapsackProblem;
import com.msu.moo.algorithms.AMultiObjectiveAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.util.Combination;

public class ExhaustiveKnapsack extends AMultiObjectiveAlgorithm<KnapsackProblem> {

	
	@Override
	public NonDominatedSolutionSet run(Evaluator<KnapsackProblem> evaluator) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		final int n = evaluator.getProblem().numOfItems();
		for (int i = 0; i <= n; i++) {
			Combination combination = new Combination(n, i);
			while (combination.hasNext()) {
				List<Boolean> l = new ArrayList<>();
				for (int j = 0; j < n; j++) l.add(false);
				int[] entries = combination.next();
				for(int entry : entries) l.set(entry, true);
				set.add(evaluator.evaluate(new BooleanPackingList(l)));
			}
		}
		return set;
	}
	

}
