package com.msu.meta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.algorithms.ExhaustiveThief;
import com.msu.moo.model.AProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.ThiefProblem;
import com.msu.thief.variable.TTPVariable;

public class FactoryThiefProblem extends AProblem<FactoryThiefVariable>{

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

	@Override
	protected List<Double> evaluate_(FactoryThiefVariable variable) {
		ThiefProblem problem = variable.get();
		NonDominatedSolutionSet set = new ExhaustiveThief().run(new Evaluator(problem));
		Set<List<Integer>> setOfTours = new HashSet<>();
		for(Solution s : set.getSolutions()) {
			TTPVariable var = (TTPVariable) s.getVariable();
			setOfTours.add(var.getTour().encode());
		}
		return Arrays.asList((double) setOfTours.size(), -(double) set.size());
	}
	

}
