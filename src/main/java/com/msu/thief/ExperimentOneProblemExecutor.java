package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;
import com.msu.thief.algorithms.impl.bilevel.ThiefBilevelHillClimbing;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

/**
 * This class allows to test an algorithm on one specific problem.
 *
 */
public class ExperimentOneProblemExecutor {

	final public static IEvaluator EVALUATOR = new StandardEvaluator(500000);

	final public static MyRandom RAND = new MyRandom(564321);

	final public static AThiefSingleObjectiveAlgorithm ALGORITHM = new ThiefBilevelHillClimbing();

	/**
	 * PROBLEMS "../ttp-benchmark/SingleObjective/10/10_5_6_25.txt";
	 * "../ttp-benchmark/SingleObjective/10/10_10_2_50.txt";
	 * "../ttp-benchmark/SingleObjective/10/10_15_10_75.txt";
	 * "../ttp-benchmark/SingleObjective/20/20_5_6_75.txt";
	 * "../ttp-benchmark/SingleObjective/20/20_20_7_50.txt";
	 * "../ttp-benchmark/SingleObjective/20/20_30_9_25.txt";
	 * "../ttp-benchmark/SingleObjective/50/50_15_8_50.txt";
	 * "../ttp-benchmark/SingleObjective/100/100_5_10_50.txt";
	 * 
	 * "../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp"
	 * 
	 * "../ttp-benchmark/json/10/10_5_6_25.json";
	 * 
	 * 
	 */

	final public static String PROBLEM = "../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp";

	public static void main(String[] args) {
		BasicConfigurator.configure();

		SingleObjectiveThiefProblem thief = null;
		if (PROBLEM.endsWith(".txt")) {
			thief = new BonyadiSingleObjectiveReader().read(PROBLEM);
		}
		if (PROBLEM.endsWith(".json")) {
			thief = (SingleObjectiveThiefProblem) new JsonThiefProblemReader().read(PROBLEM);
		}
		if (PROBLEM.endsWith(".ttp")) {
			thief = new ThiefSingleTSPLIBProblemReader().read(PROBLEM);
		}

		System.out.println(ALGORITHM);
		System.out.println(thief);
		Solution<TTPVariable> s = ALGORITHM.run(thief, Util.cloneObject(EVALUATOR), RAND);
		System.out.println(s);

	}

}
