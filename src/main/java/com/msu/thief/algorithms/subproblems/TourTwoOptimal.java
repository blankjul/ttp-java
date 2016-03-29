package com.msu.thief.algorithms.subproblems;

import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.Tour;

public class TourTwoOptimal {

	public static Tour optimize(AbstractThiefProblem problem, Tour starting) {

		// always the best value for each iteration
		Tour bestTour = starting;
		double bestTime = new StandardTimeEvaluator().evaluate(problem, bestTour, Pack.empty());

		class Swap {
			public int i;
			public int k;
			public double time;

			public Swap(int i, int k, double time) {
				super();
				this.i = i;
				this.k = k;
				this.time = time;
			}
		}

		final int numOfCities = bestTour.decode().size();

		boolean hasImproved = true;

		Swap bestSwap = null;

		while (hasImproved) {

			// when the whole iteration starts set to false
			hasImproved = false;

			for (int i = 1; i < numOfCities; i++) {
				for (int k = i + 1; k < numOfCities; k++) {
					
					double nextTime = TourSwapMutation.swapDeltaTime(bestTour, i, k, bestTime, problem.getMap());
					
					if (bestSwap == null || bestSwap.time > nextTime) {
						bestSwap = new Swap(i, k, nextTime);
						hasImproved = true;
					}
				}
			}

			if (hasImproved) {
				TourSwapMutation.swap(bestTour, bestSwap.i, bestSwap.k);
				bestTime = bestSwap.time;
			}

		}
		
		return bestTour;

	}

}
