package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.moo.ThiefMultiObjectiveAlgorithm;
import com.msu.thief.algorithms.interfaces.AThiefMultiObjectiveAlgorithm;
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
public class ExperimentMultiOneProblemExecutor {
	
	
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
		
	
	final public static AThiefMultiObjectiveAlgorithm ALGORITHM = new ThiefMultiObjectiveAlgorithm();

	
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
		
		
		NonDominatedSolutionSet<TTPVariable> set = ALGORITHM.run(new MultiObjectiveThiefProblem(thief), new Evaluator(NUM_OF_EVALUATIONS), RAND);
		
		System.out.println(ALGORITHM);
		System.out.println(thief);
		//System.out.println(set);
		
		System.out.println();
		
		for (Solution<TTPVariable> solution : set) {
			System.out.println(thief.evaluate(solution.getVariable()).getObjective(0));
		}


	}

	
}
