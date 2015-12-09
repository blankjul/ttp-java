package com.msu.thief;

import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.ObjectFactory;

public class SingleExperimentExecutor {
	
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
		
		ALGORITHMS: 
			bilevel.BiLevelEvoluationaryAlgorithm
			fixed.AntColonyOptimisation
			fixed.apriori.AprioriAlgorithm
			fixed.frequent.FrequentPatternMiningAlgorithm
			fixed.divide.DivideAndConquerAlgorithm
			fixed.topdown.TopDownHeuristicAlgorithm
		
	*/
	
	final public static boolean FIXED_TOUR_PROBLEM = false;
	final public static String PROBLEM = "../ttp-benchmark/SingleObjective/10/10_10_2_50.txt";
	final public static String ALGORITHM = "bilevel.BiLevelEvoluationaryAlgorithm";
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		IAlgorithm a = ObjectFactory.create(IAlgorithm.class,  "com.msu.thief.algorithms." + ALGORITHM);
		
		IProblem problem = null;
		
		SingleObjectiveThiefProblem thief = new BonyadiSingleObjectiveReader().read(PROBLEM);
		problem = thief;
		if (FIXED_TOUR_PROBLEM) {
			Tour<?> tour = AlgorithmUtil.calcBestTour(thief);
			problem = new SingleObjectiveThiefProblemWithFixedTour(thief, tour);
		}
		
		NonDominatedSolutionSet set = a.run(problem, new Evaluator(500000), new MyRandom(123456));

		System.out.println(problem);
		System.out.println(set.size());
		System.out.println(set);
		
		PackingList<?> b = null;
		if (FIXED_TOUR_PROBLEM) b = (PackingList<?>) set.get(0).getVariable();
		else b = ((TTPVariable) set.get(0).getVariable()).getPackingList();
			
		System.out.println(Arrays.toString(b.toIndexSet().toArray()));

	}

	
}
