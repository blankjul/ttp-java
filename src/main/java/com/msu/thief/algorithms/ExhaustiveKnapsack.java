package com.msu.thief.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.KnapsackProblem;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.util.Combination;

public class ExhaustiveKnapsack extends AbstractAlgorithm<PackingList<?>, KnapsackProblem> {

	protected NonDominatedSolutionSet set;
	
	@Override
	protected void next() {
		set = new NonDominatedSolutionSet();
		final int n = problem.numOfItems();
		for (int i = 0; i <= n; i++) {
			Combination combination = new Combination(n, i);
			while (combination.hasNext()) {
				List<Boolean> l = new ArrayList<>();
				for (int j = 0; j < n; j++) l.add(false);
				int[] entries = combination.next();
				for(int entry : entries) l.set(entry, true);
				set.add(this.problem.evaluate(new BooleanPackingList(l)));
			}
		}
	}

	@Override
	protected NonDominatedSolutionSet getResult() {
		return set;
	}

}
