package com.msu.thief.algorithms.impl;

import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.factory.PackRandomFactory;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class ThiefRandomSearch extends AThiefSingleObjectiveAlgorithm {

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem thief, IEvaluator evaluator, MyRandom rand) {
		
		ThiefFactory fac = new ThiefFactory(new TourOptimalFactory(thief), new PackRandomFactory(thief));
		RandomSearch<TTPVariable, AbstractThiefProblem> randomSearch = new RandomSearch<>(fac);
		
		NonDominatedSolutionSet<TTPVariable> s = randomSearch.run(thief, evaluator, rand);
		return s.get(0);
	}

}
