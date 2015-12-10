package com.msu.thief.algorithms.oneplusone;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IVariable;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.util.MyRandom;


/**
 * Same idea like OnePlusOneEA.
 * But it solves only the problem for a fixed tour. It is used for the bilevel approach.
 */
public class OnePlusOneEAFixedTour extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour>   {


	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {
		
		IVariable best = new EmptyPackingListFactory().next(problem, rand);
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		while (eval.hasNext()) {
			IVariable next = new BitFlipMutation().mutate(best.copy(), problem, rand);
			// check if this solution is better
			Solution s = eval.evaluate(problem, next);
			boolean isOptimal = set.add(s);
			if (isOptimal) best = next;
			
		}
		return eval.evaluate(problem, best);
	}

}
