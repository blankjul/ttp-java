package com.msu.meta;

import java.util.Arrays;
import java.util.List;

import com.msu.algorithms.ExhaustiveThief;
import com.msu.moo.model.AProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.ThiefProblem;

public class FactoryThiefProblem extends AProblem<FactoryThiefVariable>{

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

	@Override
	protected List<Double> evaluate_(FactoryThiefVariable variable) {
		ThiefProblem problem = variable.get().create();
		NonDominatedSolutionSet set = new ExhaustiveThief().run(new Evaluator(problem));
		return Arrays.asList((double) set.size());
	}
	

}
