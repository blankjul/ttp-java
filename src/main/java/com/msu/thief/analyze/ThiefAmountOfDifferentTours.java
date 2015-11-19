package com.msu.thief.analyze;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.variable.TTPVariable;

public class ThiefAmountOfDifferentTours extends AbstractAnalyzer<NonDominatedSolutionSet, Integer> {

	@Override
	public Integer analyze(NonDominatedSolutionSet set) {
		Set<List<Integer>> setOfTours = new HashSet<>();
		for (Solution s : set.getSolutions()) {
			TTPVariable var = (TTPVariable) s.getVariable();
			setOfTours.add(var.getTour().encode());
		}
		return setOfTours.size();
	}
}
