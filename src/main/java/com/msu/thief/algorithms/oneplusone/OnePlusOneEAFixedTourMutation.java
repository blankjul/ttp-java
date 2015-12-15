package com.msu.thief.algorithms.oneplusone;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;


/**
 * Same idea like OnePlusOneEA.
 * But it solves only the problem for a fixed tour. It is used for the bilevel approach.
 */
public class OnePlusOneEAFixedTourMutation extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour>   {


	final public int SWITCH_INTERVALL = 100;
	
	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {
		
		TTPVariable best = new TTPVariable(problem.getTour(), new EmptyPackingListFactory().next(problem, rand));
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		int counter = 0;
		
		while (eval.hasNext()) {
			
			// even 
			TTPVariable next = null;
			
			boolean packingOrTour = (counter++ / SWITCH_INTERVALL) % 2 == 0;
			
			if (packingOrTour) {
				PackingList<?> nextPack = (PackingList<?>) new BitFlipMutation().mutate(best.getPackingList(), problem, rand);
				next = new TTPVariable(best.getTour(), nextPack);
			} else {
				Tour<?> nextTour = (Tour<?>) new SwapMutation<>().mutate(best.getTour(), problem, rand);
				next = new TTPVariable(nextTour, best.getPackingList());
			}
			
			Solution s = eval.evaluate(problem.getProblem(), next);
			
			boolean isOptimal = set.add(s);
			if (isOptimal) {
				System.out.println(s);
				best = next;
			}
			
		}
		
		return eval.evaluate(problem.getProblem(), best);
	}

}
