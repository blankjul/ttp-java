package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.algorithms.AMultiObjectiveAlgorithm;
import com.msu.moo.algorithms.builder.NSGAIIBuilder;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.factory.PackOneItemFactory;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackSinglePointCrossover;
import com.msu.thief.ea.operators.TourEdgeRecombinationCrossover;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

/**
 * This class allows to test an algorithm on one specific problem.
 *
 */
public class ExperimentOneProblemMultiObjectiveExecutor {

	final public static IEvaluator EVALUATOR = new StandardEvaluator(1000);

	final public static MyRandom RAND = new MyRandom(564321);

	final public static String PROBLEM = "../ttp-benchmark/multi-0100-01-l.json";

	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();

		MultiObjectiveThiefProblem	thief = (MultiObjectiveThiefProblem) new JsonThiefProblemReader().read(PROBLEM);
			
		NSGAIIBuilder<TTPVariable, MultiObjectiveThiefProblem> builder = new NSGAIIBuilder<>();
		builder.set("populationSize", 20);
		builder.set("probMutation", 0.3);
		builder.set("factory", new ThiefFactory(new TourOptimalFactory(thief), new PackOneItemFactory(thief)));
		builder.set("mutation", new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(thief)));
		builder.set("crossover", new ThiefCrossover(new TourEdgeRecombinationCrossover(), new PackSinglePointCrossover(thief)));
		
		AMultiObjectiveAlgorithm<TTPVariable, MultiObjectiveThiefProblem> algorithm = builder.build();
		
		System.out.println(thief);
		NonDominatedSet<ISolution<TTPVariable>> s = algorithm.run(thief, Util.cloneObject(EVALUATOR), RAND);
		System.out.println(s);

	}

}
