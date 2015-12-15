package com.msu.thief.algorithms.oneplusone;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IVariable;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.NoMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.util.MyRandom;


public class OnePlusOneEAMutateTour extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour>   {

	
	final public int OPT = 3;

	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {
		
		AbstractThiefProblem swapTourProblem = problem.getProblem();
		IVariable best = new TTPVariable(problem.getTour(), new EmptyPackingListFactory().next(problem, rand));
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		while (eval.hasNext()) {
			
			IVariable next = null;
			if (rand.nextDouble() < 0.5) {
				next = new TTPMutation(new NoMutation<>(), new BitFlipMutation()).mutate(best.copy(), problem, rand);
			} else {
				//next = SwapTourProblemAnalyser.optimize(swapTourProblem, best, OPT).getVariable();
				next = new TTPMutation(new SwapMutation<>(), new NoMutation<>()).mutate(best.copy(), problem, rand);
			}
			
			// check if this solution is better
			Solution s = eval.evaluate(swapTourProblem, next);
			boolean isOptimal = set.add(s);
			if (isOptimal) best = next;
			
		}
		return eval.evaluate(swapTourProblem, best);
	}

}
