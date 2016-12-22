package com.msu.thief.algorithms;

import com.msu.moo.algorithms.AMultiObjectiveAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;
import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class MultiObjectiveByRentingRateVariations
		extends AMultiObjectiveAlgorithm<TTPVariable, MultiObjectiveThiefProblem> {

	public static final int RUNS = 20;

	@Override
	public NonDominatedSet<ISolution<TTPVariable>> run_(MultiObjectiveThiefProblem problem, IEvaluator evaluator,
			MyRandom rand) {

		NonDominatedSet<ISolution<TTPVariable>> result = new NonDominatedSet<>();

		Tour pi = AlgorithmUtil.calcBestTour(problem);
		Pack z = AlgorithmUtil.calcBestPackingPlan(problem.getItems(), problem.getMaxWeight());

		Solution<TTPVariable> leftUpperPoint = problem.evaluate(new TTPVariable(pi, Pack.empty()));
		Solution<TTPVariable> rightLowerPoint = problem.evaluate(new TTPVariable(pi, z));

		final Double MAX_TIME = leftUpperPoint.getObjective(0);
		final Double MIN_TIME = rightLowerPoint.getObjective(0);

		final Double MIN_PROFIT = 0.0;
		final Double MAX_PROFIT = -rightLowerPoint.getObjective(1);

		final Double ITERATION = MAX_PROFIT / (double) (RUNS - 1);

		
		
		int j = 0;
		while (true) {
			SingleObjectiveThiefProblem thief = new SingleObjectiveThiefProblem(problem, j);
			ThiefOnePlusOneEA ea = new ThiefOnePlusOneEA();
			ISolution<TTPVariable> s = ea.run(thief, Util.cloneObject(evaluator), rand);
			
			final ISolution<TTPVariable> multiSolution = problem.evaluate(s.getVariable());
			System.out.print(j + " ");
			System.out.println(multiSolution);
			
			if (multiSolution.getObjective(1) == 0) break;
			result.add(multiSolution);
			
			j++;
		}

		
		/*
		for (int i = 0; i < 20; i++) {

			SingleObjectiveThiefProblem thief = new SingleObjectiveThiefProblem(problem, (ITERATION) * i);
			ThiefOnePlusOneEA ea = new ThiefOnePlusOneEA();
			ISolution<TTPVariable> s = ea.run(thief, evaluator, rand);
			final ISolution<TTPVariable> multiSolution = problem.evaluate(s.getVariable());
			
			
			System.out.println(multiSolution);
			result.add(multiSolution);

		}
		*/

		return result;
	}

}
