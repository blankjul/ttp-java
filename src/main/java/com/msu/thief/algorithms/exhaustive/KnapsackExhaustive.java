package com.msu.thief.algorithms.exhaustive;

import java.util.HashSet;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.problems.KnapsackProblem;
import com.msu.thief.util.Combination;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.util.MyRandom;

public class KnapsackExhaustive extends AExhaustiveAlgorithm {

	
	@Override
	public NonDominatedSolutionSet run_(IProblem p, IEvaluator evaluator, MyRandom rand) {
		NonDominatedSolutionSet set = (onlyNonDominatedPoints) ? new NonDominatedSolutionSet() : new ExhaustiveSolutionSet();
		KnapsackProblem problem = (KnapsackProblem) p;
		final int n = problem.numOfItems();
		for (int i = 0; i <= n; i++) {
			Combination combination = new Combination(n, i);
			while (combination.hasNext()) {
				Set<Integer> entries = new HashSet<>(combination.next());
				set.add(evaluator.evaluate(p, new IntegerSetPackingList(entries, problem.numOfItems())));
			}
		}
		return set;
	}
	

}
