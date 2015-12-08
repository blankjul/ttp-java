package com.msu.thief.algorithms;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.thief.problems.SingleObjectiveThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.util.MyRandom;

public class OnePlusOneEAFixedTour extends ASingleObjectiveAlgorithm  {


	@Override
	public Solution run__(IProblem p, IEvaluator eval, MyRandom rand) {

		SingleObjectiveThiefProblemWithFixedTour problem = (SingleObjectiveThiefProblemWithFixedTour) p;
		
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
