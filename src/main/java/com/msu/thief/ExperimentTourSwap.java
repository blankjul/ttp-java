package com.msu.thief;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.thief.algorithms.tour.EvolutionOnRelevantItems;
import com.msu.thief.algorithms.tour.FixedTourSingleObjectiveThiefProblem;
import com.msu.thief.ea.tour.mutation.ThiefSwapMutation;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;

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

		Solution best = new EvolutionOnRelevantItems().run_(new FixedTourSingleObjectiveThiefProblem(thief, tour), eval,
				rand);

		System.out.println(best);

		final int SIZE_OF_POOL = 20;

		boolean improvement = true;
		double time = new StandardTimeEvaluator().evaluate(thief, tour, Pack.empty());

		PrintWriter writer = new PrintWriter("data.csv", "UTF-8");

		class Swap {
			final public int i;
			final public int j;
			final public double time;

			public Swap(int i, int j, double time) {
				super();
				this.i = i;
				this.j = j;
				this.time = time;
			}
		}

		// while (improvement) {

		improvement = false;

		List<Swap> nextSwaps = new ArrayList<>();

		outer: for (int i = thief.numOfCities() - 1; i > 0; i--) {

			for (int k = i - 1; k > 0; k--) {

				Tour next = ((TTPVariable) best.getVariable()).getTour().copy();

				double nextTime = ThiefSwapMutation.swapDeltaTime(next, i, k, time, thief.getMap());
				ThiefSwapMutation.swap(next, i, k);
				// double nextTime = new StandardTimeEvaluator().evaluate(thief,
				// next, Pack.empty());
				
				//nextSwaps.add(new Swap(i, j, nextTime))

				Solution opt = new EvolutionOnRelevantItems()
						.run_(new FixedTourSingleObjectiveThiefProblem(thief, next), eval, rand);

				writer.println(String.format("%s,%s,%s", nextTime, opt.getObjective(0), i, k));

				if (new SolutionDominatorWithConstraints().isDominating(opt, best)) {
					best = opt;
					System.out.println(String.format("%s %s %s swap %s", nextTime, best.getObjective(0), i, k, next));
					improvement = true;
					// break outer;
				}

			}
		}

		// }

		System.out.println(best);
		writer.close();
	}
}
