package com.msu.thief.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class BilevelAlgorithmsFixedTour extends ASingleObjectiveAlgorithm {

	//! algorithm that solves the SingleObjectiveThiefProblemWithFixedTour
	protected IAlgorithm algorithm;
	
	protected int numOf2OptTours = 0;
	
	
	public BilevelAlgorithmsFixedTour() {
		super();
	}
	
	
	public BilevelAlgorithmsFixedTour(IAlgorithm algorithm) {
		super();
		this.algorithm = algorithm;
		this.name = String.format("BILEVEL-%s", algorithm.toString());
	}
	

	public BilevelAlgorithmsFixedTour(IAlgorithm algorithm, int numOf2OptTours) {
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
		
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		Map<Solution, ThiefProblemWithFixedTour> mapToFixedTour = new HashMap<>();
		
		for (ThiefProblemWithFixedTour fixedTourProblem : problems) {
			Solution s = run___(fixedTourProblem, new Evaluator(evaluator.getMaxEvaluations() / problems.size()), rand);
			set.add(s);
			mapToFixedTour.put(s, fixedTourProblem);
		}


		// reevaluate with the tour
		Solution best = set.get(0);
		IVariable var = best.getVariable();
		if (var instanceof PackingList<?>) {
			TTPVariable thiefVar = new TTPVariable(mapToFixedTour.get(best).getTour(), (PackingList<?>) best.getVariable());
			best = problem.evaluate(thiefVar);
		}
		
		return best;
	}

	
	public Solution run___(ThiefProblemWithFixedTour p, IEvaluator evaluator, MyRandom rand) {
		return algorithm.run(p, evaluator, rand).get(0);
	}

	
	

}
