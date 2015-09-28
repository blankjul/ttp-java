package com.msu.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.KnapsackProblem;
import com.msu.knp.model.BooleanPackingList;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.util.Combination;

public class ExhaustiveKnapsack extends AbstractAlgorithm {

	
	@Override
	public NonDominatedSolutionSet run(IEvaluator evaluator) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		KnapsackProblem problem = (KnapsackProblem) evaluator.getProblem();
		final int n = problem.numOfItems();
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
