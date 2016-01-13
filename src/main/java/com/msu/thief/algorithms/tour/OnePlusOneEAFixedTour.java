package com.msu.thief.algorithms.tour;

import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AFixedTourAlgorithm;
import com.msu.thief.ea.pack.mutation.ThiefBitflipMutation;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.util.MyRandom;


/**
 * Same idea like OnePlusOneEA.
 * But it solves only the problem for a fixed tour. It is used for the bilevel approach.
 */
public class OnePlusOneEAFixedTour extends AFixedTourAlgorithm {

	//! best packing list found so far
	protected Pack best = null;
	
	
	public OnePlusOneEAFixedTour() {
		super();
	}


	public OnePlusOneEAFixedTour(Pack best) {
		super();
		this.best = best;
	}


	@Override
	public Solution run_(FixedTourSingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {
		
		if (best == null) best = new Pack();
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		while (eval.hasNext()) {
			
			Pack next =  best.copy();
			new ThiefBitflipMutation().mutate(problem.getProblem(), rand, next);
			
			// check if this solution is better
			Solution s = eval.evaluate(problem, next);
			boolean isBetter = set.add(s);
			
			if (isBetter) best = next;
			
		}
		
		return eval.evaluate(problem.getProblem(), TTPVariable.create(problem.getTour(), best));
	}

}