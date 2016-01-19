package com.msu.thief.algorithms.impl.bilevel.pack;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.interfaces.AFixedPackSingleObjectiveAlgorithm;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedPack;
import com.msu.thief.problems.variable.Tour;

public class FixedPackSwapTour extends AFixedPackSingleObjectiveAlgorithm {

	//! tour to start with the optimization
	protected Tour tour = null;
	
	protected int slidingWindowSize = 20;
	
	
	public FixedPackSwapTour(Tour tour) {
		super();
		this.tour = tour;
	}


	@Override
	public Solution<Tour> run(ThiefProblemWithFixedPack problem, IEvaluator evaluator, MyRandom rand) {

		final AbstractThiefProblem thief = problem.getProblem();
		
		Solution<Tour> best = evaluator.evaluate(problem, tour); 
		
		
		boolean improvement = true;
		while (improvement) {

			improvement = false;

			
			outer: 
				for (int i = thief.numOfCities() - 1; i > 0; i--) {

				for (int k = i - 1; k > 0; k--) {
					
					if (i - k > slidingWindowSize) break;

					Tour next = best.getVariable().copy();
					TourSwapMutation.swap(next, i, k);
					
					// let the packing plan fixed
					Solution<Tour> opt = evaluator.evaluate(problem, next);
					
					if (new SolutionDominatorWithConstraints().isDominating(opt, best)) {
						best = opt;
						//System.out.println(String.format("%s %s %s", best.getObjectives(), i, k));
						improvement = true;
						break outer;
					}

				}
			}
			
		}
		
		return best;
	}


	

}
