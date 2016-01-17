package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.tour.FixedTourEvolutionOnRelevantItems;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.Tour;

/**
 * This class tests if the algorithm is able to find the optimum given a fixed tour.
 * for the berlin example.
 * 
 * the solution should be 
 * -4031.230818038056,0.0
 * [0, 1, 2, 3, 42, 15, 48, 16, 17, 49, 18, 50, 19, 20, 29]"
 * 
 */
public class ExperimentFixedTour {

	public static void main(String[] args) {

		BasicConfigurator.configure();

		SingleObjectiveThiefProblem thief = new ThiefSingleTSPLIBProblemReader()
				.read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");

		MyRandom rand = new MyRandom(123456);

		Tour tour = Tour.createFromString(
				"0, 48, 31, 44, 18, 40, 7, 8, 9, 42, 32, 50, 10, 51, 13, 12, 46, 25, 26, 27, 11, 24, 3, 5, 14, 4, 23, 47, 37, 36, 39, 38, 35, 34, 33, 43, 45, 15, 28, 49, 19, 22, 29, 1, 6, 41, 20, 16, 2, 17, 30, 21]");
/*
		Solution best = new OnePlusOneEAFixedTour().run(new FixedTourSingleObjectiveThiefProblem(thief, tour),
				new Evaluator(500000), rand);
*/
		
		Solution<Pack> best = new FixedTourEvolutionOnRelevantItems().run(new ThiefProblemWithFixedTour(thief, tour),
				new Evaluator(500000), rand);
		
		System.out.println(best);

	}
}
