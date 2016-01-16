package com.msu.thief.algorithms.impl.tour;

import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.interfaces.IFixedTourSingleObjectiveAlgorithm;
import com.msu.thief.ea.operators.ThiefBitflipMutation;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.util.MyRandom;


/**
 * Same idea like OnePlusOneEA.
 * But it solves only the problem for a fixed tour. It is used for the bilevel approach.
 */
public class FixedTourOnePlusOneEA implements IFixedTourSingleObjectiveAlgorithm {

	//! best packing list found so far
	protected Pack best = null;
	
	
	public FixedTourOnePlusOneEA() {
		super();
	}


	public FixedTourOnePlusOneEA(Pack best) {
		super();
		this.best = best;
	}


	@Override
	public Solution<Pack> run(ThiefProblemWithFixedTour problem, IEvaluator evaluator, MyRandom rand) {
		
		if (best == null) best = new Pack();
		
		NonDominatedSolutionSet<Pack> set = new NonDominatedSolutionSet<>();

		while (evaluator.hasNext()) {
			
			Pack next =  best.copy();
			new ThiefBitflipMutation(problem.getProblem()).mutate(next, rand);
			
			// check if this solution is better
			Solution<Pack> s = evaluator.evaluate(problem, next);
			boolean isBetter = set.add(s);
			
			if (isBetter) best = next;
			
		}
		
		return evaluator.evaluate(problem, best);
	}

}
