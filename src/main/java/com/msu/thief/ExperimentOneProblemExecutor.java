package com.msu.thief;

import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.algorithms.AlternatingPoolingEvolution;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class ExperimentOneProblemExecutor {
	
	/**
		PROBLEMS
			"../ttp-benchmark/SingleObjective/10/10_5_6_25.txt";
			"../ttp-benchmark/SingleObjective/10/10_10_2_50.txt";
			"../ttp-benchmark/SingleObjective/10/10_15_10_75.txt";
			"../ttp-benchmark/SingleObjective/20/20_5_6_75.txt";
			"../ttp-benchmark/SingleObjective/20/20_20_7_50.txt";
			"../ttp-benchmark/SingleObjective/20/20_30_9_25.txt";
			"../ttp-benchmark/SingleObjective/50/50_15_8_50.txt";
			"../ttp-benchmark/SingleObjective/100/100_5_10_50.txt";
			
			"../ttp-benchmark/json/10/10_5_6_25.json";
		
		ALGORITHMS
		
			bilevel.BiLevelEvoluationaryAlgorithm
			bilevel.SolveKnapsackWithEmptyHeuristicValues
			bilevel.AntColonyOptimisation
			bilevel.GreedyPackingAlgorithm
			bilevel.apriori.AprioriAlgorithm
			bilevel.frequent.FrequentPatternMiningAlgorithm
			bilevel.divide.DivideAndConquerAlgorithm
			bilevel.topdown.TopDownHeuristicAlgorithm
		
	*/
	
	final public static boolean FIXED_TOUR_PROBLEM = false;
	
	final public static String PROBLEM = "../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp";
	final public static int NUM_OF_EVALUATIONS = 500000;
	
/*	
	final public static IAlgorithm ALGORITHM = ObjectFactory.create(IAlgorithm.class,  
			"com.msu.thief.algorithms." + "bilevel.AntColonyOptimisation");
	*/
	
/*	
	final public static  IAlgorithm ALGORITHM = new Builder<SingleThiefEvoluation>(SingleThiefEvoluation.class)
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
			.set("crossover", new TTPCrossover(new NoCrossover<>(), new HalfUniformCrossover<>()))
			.set("mutation", new TTPMutation(new NoMutation<>(), new BitFlipMutation()))
			.set("name", "EA-HUX").build();
	*/
	
	final public static  IAlgorithm ALGORITHM = new AlternatingPoolingEvolution();
	//final public static IAlgorithm ALGORITHM = new BilevelAlgorithmsFixedTour(new OnePlusOneEAFixedTourMutation());
	//final public static IAlgorithm ALGORITHM = new BiLevelEvoluationaryAlgorithm();
	//final public static IAlgorithm ALGORITHM = new CoevolutionAlgorithm();
	//final public static IAlgorithm ALGORITHM = new SolveKnapsackWithHeuristicValues();
	
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		
		SingleObjectiveThiefProblem thief = null;
		if (PROBLEM.endsWith(".txt")) {
			thief = new BonyadiSingleObjectiveReader().read(PROBLEM);
		} if (PROBLEM.endsWith(".json")) {
			thief = (SingleObjectiveThiefProblem) new JsonThiefProblemReader().read(PROBLEM);
		} if (PROBLEM.endsWith(".ttp")) {
			thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader().read(PROBLEM);
		}
		
		//thief.setToMultiObjective(true);
		//System.out.println(thief.evaluate(new TTPVariable(AlgorithmUtil.calcBestTour(thief), new EmptyPackingListFactory().next(thief, null))));
		
		
		IProblem problem = thief;
		if (FIXED_TOUR_PROBLEM) {
			Tour<?> tour = AlgorithmUtil.calcBestTour(thief);
			problem = new ThiefProblemWithFixedTour(thief, tour);
		}
		
		NonDominatedSolutionSet set = ALGORITHM.run(problem, new Evaluator(NUM_OF_EVALUATIONS), new MyRandom(123412));
		
		System.out.println(ALGORITHM);
		System.out.println(problem);
		System.out.println(set.size());
		System.out.println(set);
		
		PackingList<?> b = null;
		if (FIXED_TOUR_PROBLEM) b = (PackingList<?>) set.get(0).getVariable();
		else b = ((TTPVariable) set.get(0).getVariable()).getPackingList();
			
		System.out.println(Arrays.toString(b.toIndexSet().toArray()));

	}

	
}
