package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.algorithms.ASingleObjectiveAlgorithm;
import com.msu.moo.algorithms.builder.NSGAIIBuilder;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;
import com.msu.thief.algorithms.ThiefOnePlusOneEA;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.factory.PackOneItemFactory;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackSinglePointCrossover;
import com.msu.thief.ea.operators.TourOrderedCrossover;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
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

	final public static String PROBLEM = "../ttp-benchmark/multi-0100-01-l.json";

	
	
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
		
		NSGAIIBuilder<TTPVariable, MultiObjectiveThiefProblem> builder = new NSGAIIBuilder<>();
		builder.set("name", "NSGAII-One");
		builder.set("populationSize", 20);
		builder.set("probMutation", 0.3);
		builder.set("factory", new ThiefFactory(new TourOptimalFactory(thief), new PackOneItemFactory(thief)));
		builder.set("mutation", new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(thief)));
		builder.set("crossover", new ThiefCrossover(new TourOrderedCrossover(), new PackSinglePointCrossover(thief)));
		System.out.println(builder.build());
		
		
		System.out.println(thief);
		ISolution<TTPVariable> s = ALGORITHM.run(thief, Util.cloneObject(EVALUATOR), RAND);
		System.out.println(s);

	}

}
