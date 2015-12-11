package com.msu.thief;

import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.ObjectFactory;

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
		
		ALGORITHMS: 
			bilevel.BiLevelEvoluationaryAlgorithm
			bilevel.SolveKnapsackWithEmptyHeuristicValues
			bilevel.AntColonyOptimisation
			bilevel.GreedyPackingAlgorithm
			bilevel.apriori.AprioriAlgorithm
			bilevel.frequent.FrequentPatternMiningAlgorithm
			bilevel.divide.DivideAndConquerAlgorithm
			bilevel.topdown.TopDownHeuristicAlgorithm
		
	*/
	
	final public static boolean FIXED_TOUR_PROBLEM = true;
	final public static String PROBLEM = "../ttp-benchmark/SingleObjective/10/10_10_2_50.txt";
	
	final public static IAlgorithm ALGORITHM = ObjectFactory.create(IAlgorithm.class,  
			"com.msu.thief.algorithms." + "bilevel.AntColonyOptimisation");
	
	//final public static IAlgorithm ALGORITHM = new BiLevelAlgorithms(new SolveKnapsackWithEmptyHeuristicValues());
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		
		SingleObjectiveThiefProblem thief = null;
		if (PROBLEM.endsWith(".txt")) {
			thief = new BonyadiSingleObjectiveReader().read(PROBLEM);
		} if (PROBLEM.endsWith(".json")) {
			thief = (SingleObjectiveThiefProblem) new JsonThiefProblemReader().read(PROBLEM);
		}
		
		//thief.setToMultiObjective(true);
		//System.out.println(thief.evaluate(new TTPVariable(AlgorithmUtil.calcBestTour(thief), new EmptyPackingListFactory().next(thief, null))));
		
		
		IProblem problem = thief;
		if (FIXED_TOUR_PROBLEM) {
			Tour<?> tour = AlgorithmUtil.calcBestTour(thief);
			problem = new ThiefProblemWithFixedTour(thief, tour);
		}
		
		NonDominatedSolutionSet set = ALGORITHM.run(problem, new Evaluator(500000), new MyRandom(123456));

		System.out.println(problem);
		System.out.println(set.size());
		System.out.println(set);
		
		PackingList<?> b = null;
		if (FIXED_TOUR_PROBLEM) b = (PackingList<?>) set.get(0).getVariable();
		else b = ((TTPVariable) set.get(0).getVariable()).getPackingList();
			
		System.out.println(Arrays.toString(b.toIndexSet().toArray()));

	}

	
}
