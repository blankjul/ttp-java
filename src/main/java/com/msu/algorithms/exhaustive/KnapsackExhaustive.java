package com.msu.algorithms.exhaustive;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.problems.KnapsackProblem;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.util.Combination;
import com.msu.util.Random;

public class KnapsackExhaustive extends AExhaustiveAlgorithm {

	
	@Override
	public NonDominatedSolutionSet run_(IProblem p, IEvaluator evaluator, Random rand) {
		NonDominatedSolutionSet set = (onlyNonDominatedPoints) ? new NonDominatedSolutionSet() : new ExhaustiveSolutionSet();
		KnapsackProblem problem = (KnapsackProblem) p;
		final int n = problem.numOfItems();
		for (int i = 0; i <= n; i++) {
			Combination combination = new Combination(n, i);
			while (combination.hasNext()) {
				List<Boolean> l = new ArrayList<>();
				for (int j = 0; j < n; j++) l.add(false);
				int[] entries = combination.next();
				for(int entry : entries) l.set(entry, true);
				set.add(evaluator.evaluate(p, new BooleanPackingList(l)));
			}
		}
		return set;
	}
	

}
