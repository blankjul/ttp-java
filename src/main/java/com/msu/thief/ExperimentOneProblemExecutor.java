package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.ASingleObjectiveAlgorithm;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;
import com.msu.thief.algorithms.ThiefOnePlusOneEA;
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

	final public static ASingleObjectiveAlgorithm<TTPVariable, SingleObjectiveThiefProblem> ALGORITHM = new ThiefOnePlusOneEA();

	final public static String PROBLEM = "../json-single-objective/single-0005-01-l.json";

	
	
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
