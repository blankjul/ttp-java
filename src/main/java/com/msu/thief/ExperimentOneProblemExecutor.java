package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.impl.ThiefBilevelPackOnHeuristicFront;
import com.msu.thief.algorithms.interfaces.IThiefSingleObjectiveAlgorithm;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.util.MyRandom;


/**
 * This class allows to test an algorithm on one specific problem. 
 *
 */
public class ExperimentOneProblemExecutor {
	
	
	final public static int NUM_OF_EVALUATIONS = 500000;
	
	final public static MyRandom RAND = new MyRandom(654321);
	
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
			
			"../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp"
			
			"../ttp-benchmark/json/10/10_5_6_25.json";
		
		
	*/
	
	final public static String PROBLEM = "../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp";
	
	/**
	 
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
		
	
	final public static IThiefSingleObjectiveAlgorithm ALGORITHM = new ThiefBilevelPackOnHeuristicFront();
	//final public static IThiefAlgorithm ALGORITHM = new ThiefEvolutionaryLocalPackAlgorithm();
	
	//final public static  IAlgorithm ALGORITHM = new AlternatingPoolingEvolution();
	//final public static IAlgorithm ALGORITHM = new BilevelAlgorithmsFixedTour(new OnePlusOneEAFixedTourMutation());
	//final public static IAlgorithm ALGORITHM = new CoevolutionAlgorithm();
	//final public static IAlgorithm ALGORITHM = new TwoPhaseEvolution();
	//final public static IAlgorithm ALGORITHM = new BilevelAlgorithmsFixedTour(new GreedyPackingWithHeuristics());
	
	
	
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
		
		Solution<TTPVariable> set = ALGORITHM.run(thief, new Evaluator(NUM_OF_EVALUATIONS), RAND);
		
		System.out.println(ALGORITHM);
		System.out.println(thief);
		System.out.println(set);
		


	}

	
}
