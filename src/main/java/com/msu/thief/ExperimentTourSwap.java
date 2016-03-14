package com.msu.thief;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.evaluator.ConvergenceEvaluator;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.bilevel.tour.FixedTourOnePlusOneEAHeuristicItems;
import com.msu.thief.ea.factory.TourRandomFactory;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.Tour;

/**
 * This class tests if the algorithm is able to find the optimum given a fixed
 * tour. for the berlin example.
 * 
 * the solution should be -4031.230818038056,0.0 [0, 1, 2, 3, 42, 15, 48, 16,
 * 17, 49, 18, 50, 19, 20, 29]"
 * 
 */
public class ExperimentTourSwap {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

		BasicConfigurator.configure();

		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader()
				.read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");
		MyRandom rand = new MyRandom(123456);
		IEvaluator eval = new StandardEvaluator(Integer.MAX_VALUE);

		//Tour tour = Tour.createFromString(
		//		"0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12, 46, 25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 36, 39, 38, 35, 34, 33, 43, 45, 15, 28, 49, 19, 22, 29, 1, 6, 41, 20, 16, 2, 17, 30, 21]");

		// tour = new TourRandomFactory(thief).next(rand);

		for (int x = 0; x < 100; x++) {

			Tour bestTour = new TourRandomFactory(thief).next(rand);
			Pack bestPack = new FixedTourOnePlusOneEAHeuristicItems()
					.run(new ThiefProblemWithFixedTour(thief, bestTour), new ConvergenceEvaluator(100), rand)
					.getVariable();
			Solution<Pack> best = eval.evaluate(new ThiefProblemWithFixedTour(thief, bestTour), bestPack);

			// System.out.println(String.format("%s", best));

			boolean improvement = true;

			while (improvement) {

				improvement = false;

				outer: for (int i = thief.numOfCities() - 1; i > 0; i--) {

					for (int k = i - 1; k > 0; k--) {

						if (i - k > 20)
							break;
						Tour next = bestTour.copy();
						TourSwapMutation.swap(next, i, k);

						Solution<Pack> opt = new ThiefProblemWithFixedTour(thief, next).evaluate(best.getVariable());
						// Solution<Pack> opt = new
						// FixedTourOnePlusOneEAHeuristicItems()
						// .run(new ThiefProblemWithFixedTour(thief, next), new
						// ConvergenceEvaluator(100), rand);

						if (SolutionDominator.isDominating(opt, best)) {
							best = opt;
							bestTour = next;
							// System.out.println(String.format("%s %s %s", i,
							// k, best));
							improvement = true;
							break outer;
						}

					}
				}

			}

			improvement = true;

			while (improvement) {

				improvement = false;

				outer: for (int i = thief.numOfCities() - 1; i > 0; i--) {

					for (int k = i - 1; k > 0; k--) {

						if (i - k > 20)
							break;
						Tour next = bestTour.copy();
						TourSwapMutation.swap(next, i, k);

						Solution<Pack> opt = new FixedTourOnePlusOneEAHeuristicItems()
								.run(new ThiefProblemWithFixedTour(thief, next), new ConvergenceEvaluator(100), rand);

						if (SolutionDominator.isDominating(opt, best)) {
							best = opt;
							bestTour = next;
							// System.out.println(String.format("%s %s %s", i,
							// k, best));
							improvement = true;
							break outer;
						}

					}
				}

			}

			System.out.println(String.format("%s %s %s", best.getObjectives(), bestPack, bestTour));
		}
	}
}
