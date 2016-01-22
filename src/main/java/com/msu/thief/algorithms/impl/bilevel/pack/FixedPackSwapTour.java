package com.msu.thief.algorithms.impl.bilevel.pack;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.interfaces.AFixedPackSingleObjectiveAlgorithm;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.ThiefProblemWithFixedPack;
import com.msu.thief.problems.variable.Tour;

public class FixedPackSwapTour extends AFixedPackSingleObjectiveAlgorithm {

	// ! tour to start with the optimization
	protected Tour tour = null;

	protected int slidingWindowSize = 20;

	public FixedPackSwapTour(Tour tour) {
		super();
		this.tour = tour;
	}

	@Override
	public Solution<Tour> run(ThiefProblemWithFixedPack problem, IEvaluator evaluator, MyRandom rand) {

		Solution<Tour> best = evaluator.evaluate(problem, tour);

		while (true) {

			// local optimize until one improvement
			Solution<Tour> next = localSwaps(problem, evaluator, best, slidingWindowSize);

			// if no improvement was found - we are done
			if (next.equals(best)) break;
			else best = next;

		}

		return best;
	}

	public static Solution<Tour> localSwaps(ThiefProblemWithFixedPack thief, IEvaluator evaluator, Solution<Tour> s,
			Integer slidingWindowSize) {

		// for every city backwards
		for (int i = thief.getProblem().numOfCities() - 1; i > 0; i--) {

			// starting from i go to the left
			for (int k = i - 1; k > 0; k--) {

				// if sliding window is defined be aware of it
				if (slidingWindowSize != null && i - k > slidingWindowSize)
					break;

				// do the swap
				Tour next = s.getVariable().copy();
				TourSwapMutation.swap(next, i, k);

				// let the packing plan fixed
				Solution<Tour> opt = evaluator.evaluate(thief, next);

				// better tour found
				if (SolutionDominator.isDominating(opt, s)) {
					//System.out.println(String.format("%s %s %s", opt.getObjectives(), i, k));
					return opt;
				}

			}
		}

		// no better solution was found
		return s;

	}

}
