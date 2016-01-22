package com.msu.thief.algorithms.impl.bilevel.tour;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;

public class FixedTourOnePlusOneEAHeuristicItems extends FixedTourOnePlusOneEA {

	
	@Override
	public Solution<Pack> run(ThiefProblemWithFixedTour problem, IEvaluator evaluator, MyRandom rand) {
		best = new FixedTourKnapsackWithHeuristic(false).run(problem, evaluator, rand).getVariable();

		// only mutation on this items
		PackBitflipMutation mutation = new PackBitflipMutation(problem.getProblem(), best.decode());

		NonDominatedSolutionSet<Pack> set = new NonDominatedSolutionSet<>();
		set.add(evaluator.evaluate(problem, best));

		while (evaluator.hasNext()) {

			Pack next = best.copy();
			mutation.mutate(next, rand);

			// check if this solution is better
			Solution<Pack> s = evaluator.evaluate(problem, next);
			boolean isBetter = set.add(s);

			if (isBetter)
				best = next;

			evaluator.notify(set.getSolutions());

		}

		Solution<Pack> s = evaluator.evaluate(problem, best);
		
		return s;

	}

}
