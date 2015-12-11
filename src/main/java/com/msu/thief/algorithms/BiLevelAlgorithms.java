package com.msu.thief.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class BiLevelAlgorithms extends ASingleObjectiveAlgorithm {

	//! algorithm that solves the SingleObjectiveThiefProblemWithFixedTour
	protected IAlgorithm algorithm;
	
	protected int numOf2OptTours = 0;
	
	
	public BiLevelAlgorithms() {
		super();
	}
	
	
	public BiLevelAlgorithms(IAlgorithm algorithm) {
		super();
		this.algorithm = algorithm;
	}
	

	public BiLevelAlgorithms(IAlgorithm algorithm, int numOf2OptTours) {
		this(algorithm);
		this.numOf2OptTours = numOf2OptTours;
	}


	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {

		// cast to thief problem
		SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) p;

		// best tour for the given tsp
		Tour<?> bestTour = AlgorithmUtil.calcBestTour(problem);

		// create a pool of subproblems with fixed tours
		List<ThiefProblemWithFixedTour> problems = new ArrayList<>();
		problems.add(new ThiefProblemWithFixedTour(problem, bestTour));
		problems.add(new ThiefProblemWithFixedTour(problem, bestTour.getSymmetric()));
		
		
		// add other two opt optimal tours as well
		for (int i = 0; i < numOf2OptTours; i++) {
			Tour<?> tour = (Tour<?>) new SwapMutation<>().mutate(bestTour, problem, rand);
			problems.add(new ThiefProblemWithFixedTour(problem, tour));
		}
		
		
		List<Double> objectives = new ArrayList<>();
		List<Solution> solutions = new ArrayList<>();

		for (ThiefProblemWithFixedTour fixedTourProblem : problems) {
			Solution s = run___(fixedTourProblem, new Evaluator(evaluator.getMaxEvaluations() / problems.size()), rand);
			solutions.add(s);
			objectives.add(s.getObjectives(0));
		}

		// best solution which was found
		int bestIndex = objectives.indexOf(Collections.min(objectives));

		// reevaluate with the tour
		TTPVariable var = new TTPVariable(problems.get(bestIndex).getTour(),
				(PackingList<?>) solutions.get(bestIndex).getVariable());
		Solution best = problem.evaluate(var);

		return best;
	}

	
	public Solution run___(ThiefProblemWithFixedTour p, IEvaluator evaluator, MyRandom rand) {
		return algorithm.run(p, evaluator, rand).get(0);
	}


	@Override
	public String toString() {
		return "BILEVEL-" + algorithm.toString();
	}
	
	
	

}
