package com.msu.thief.algorithms.impl.bilevel;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.evaluator.ConvergenceEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.bilevel.tour.FixedTourOnePlusOneEAHeuristicItems;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedPack;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefBilevelHillClimbing extends AThiefSingleObjectiveAlgorithm {

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem thief, IEvaluator evaluator, MyRandom rand) {


		return swapsWithLocalOptimization(thief, rand, evaluator);
	}

	public static Solution<TTPVariable> swapsWithLocalOptimization(SingleObjectiveThiefProblem thief, MyRandom rand,
			IEvaluator evaluator) {

		//Tour bestTour = AlgorithmUtil.calcBestTour(thief);
		
		Tour bestTour = Tour.createFromString(
				"0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12, 46, 25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 36, 39, 38, 35, 34, 33, 43, 45, 15, 28, 49, 19, 22, 29, 1, 6, 41, 20, 16, 2, 17, 30, 21]");

		
		Pack bestPack = new FixedTourOnePlusOneEAHeuristicItems()
				.run(new ThiefProblemWithFixedTour(thief, bestTour), new ConvergenceEvaluator(100), rand).getVariable();
		Solution<Pack> best = evaluator.evaluate(new ThiefProblemWithFixedTour(thief, bestTour), bestPack);

		boolean improvement = true;

		while (improvement) {

			improvement = false;

			// for every city backwards
			outer:
			for (int i = thief.numOfCities() - 1; i > 0; i--) {

				// starting from i go to the left
				for (int k = i - 1; k > 0; k--) {

					// if sliding window is defined be aware of it
					if (i - k > 20)
						break;

					// do the swap
					Tour next = bestTour.copy();
					TourSwapMutation.swap(next, i, k);

					// let the packing plan fixed
					Solution<Pack> opt = new FixedTourOnePlusOneEAHeuristicItems()
							.run(new ThiefProblemWithFixedTour(thief, next), new ConvergenceEvaluator(100), rand);

					// better tour found
					if (SolutionDominator.isDominating(opt, best)) {
						best = opt;
						bestTour = next;
						System.out.println(String.format("%s %s %s", i, k, best));
						improvement = true;
						break outer;
					}
				}
			}
		}

		// no better solution was found
		return evaluator.evaluate(thief, TTPVariable.create(bestTour, best.getVariable()));

	}

}
