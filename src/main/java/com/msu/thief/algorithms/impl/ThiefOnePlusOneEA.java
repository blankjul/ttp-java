package com.msu.thief.algorithms.impl;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.bilevel.tour.FixedTourOnePlusOneEA;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefOnePlusOneEA extends AThiefSingleObjectiveAlgorithm{

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {
		
		// find best tour
		Tour tour = AlgorithmUtil.calcBestTour(problem);
		
		// optimize 1+1 fixing this tour
		Solution<Pack> s = new FixedTourOnePlusOneEA().run(new ThiefProblemWithFixedTour(problem, tour), evaluator, rand);
		
		// evaluate the result
		Solution<TTPVariable> result = problem.evaluate(TTPVariable.create(tour, s.getVariable()));
		
		return result;
	}

}
