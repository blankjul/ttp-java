package com.msu.thief.algorithms;

import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.tour.FixedTourSingleObjectiveThiefProblem;
import com.msu.util.MyRandom;

public abstract class AFixedTourAlgorithm implements IAlgorithm {
	
	public abstract Solution run_(FixedTourSingleObjectiveThiefProblem thief, IEvaluator eval, MyRandom rand);

	@Override
	public NonDominatedSolutionSet run(IProblem problem, IEvaluator evaluator, MyRandom rand) {
		Solution s =  run_((FixedTourSingleObjectiveThiefProblem)problem, evaluator, rand);
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		set.add(s);
		return set;
	}

	@Override
	public void setName(String name) {
	}


}
