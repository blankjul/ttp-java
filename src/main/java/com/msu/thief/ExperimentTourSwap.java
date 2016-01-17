package com.msu.thief;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.tour.FixedTourEvolutionOnRelevantItems;
import com.msu.thief.ea.operators.ThiefSwapMutation;
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
		IEvaluator eval = new Evaluator(Integer.MAX_VALUE);

		Tour tour = Tour.createFromString(
				"0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12, 46, 25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 36, 39, 38, 35, 34, 33, 43, 45, 15, 28, 49, 19, 22, 29, 1, 6, 41, 20, 16, 2, 17, 30, 21]");

		
		// Solution<Pack> best = ;
		
		Tour bestTour = tour;
		//Pack bestPack = new Pack(Arrays.asList(0, 1, 2, 3, 42, 15, 16, 48, 17, 49, 18, 50, 19, 20, 29));
		Pack bestPack = new FixedTourEvolutionOnRelevantItems().run(new ThiefProblemWithFixedTour(thief, tour), eval, rand).getVariable();
		Solution<Pack> best = eval.evaluate(new ThiefProblemWithFixedTour(thief, bestTour), bestPack);
		
		
		System.out.println(String.format("%s %s %s", best.getObjectives(), bestPack, bestTour));
		
		
		boolean improvement = true;
		
		while (improvement) {

			improvement = false;

			
			outer: 
				for (int i = thief.numOfCities() - 1; i > 0; i--) {

				for (int k = i - 1; k > 0; k--) {

					Tour next = bestTour.copy();
					
					ThiefSwapMutation.swap(next, i, k);
					//double nextTime = ThiefSwapMutation.swapDeltaTime(next, i, k, time, thief.getMap());
					//double nextTime = new StandardTimeEvaluator().evaluate(thief, next, Pack.empty());
					
					Solution<Pack> opt = new FixedTourEvolutionOnRelevantItems()
							.run(new ThiefProblemWithFixedTour(thief, next), eval, rand);

					
					if (new SolutionDominatorWithConstraints().isDominating(opt, best)) {
						best = opt;
						bestTour = next;
						bestPack = opt.getVariable();
						System.out.println(String.format("%s %s %s", best.getObjectives(), i, k));
						improvement = true;
						break outer;
					}

				}
			}
			
		}

		System.out.println(String.format("%s %s %s", best.getObjectives(), bestPack, bestTour));
	}
}
