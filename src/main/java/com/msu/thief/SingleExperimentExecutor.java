package com.msu.thief;

import java.util.Arrays;

import org.apache.log4j.BasicConfigurator;

import com.msu.builder.Builder;
import com.msu.interfaces.IAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.ThiefSingleObjectiveEvolutionaryAsSetAlgorithm;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;
import com.msu.util.MyRandom;

public class SingleExperimentExecutor {
	
	/*
	"../ttp-benchmark/SingleObjective/10/10_5_6_25.txt";
	"../ttp-benchmark/SingleObjective/10/10_10_2_50.txt";
	"../ttp-benchmark/SingleObjective/10/10_15_10_75.txt";
	"../ttp-benchmark/SingleObjective/20/20_5_6_75.txt";
	"../ttp-benchmark/SingleObjective/20/20_20_7_50.txt";
	"../ttp-benchmark/SingleObjective/20/20_30_9_25.txt";
	"../ttp-benchmark/SingleObjective/50/50_15_8_50.txt";
	"../ttp-benchmark/SingleObjective/100/100_5_10_50.txt";
	*/
	
	final public static String PROBLEM = "../ttp-benchmark/SingleObjective/10/10_5_6_25.txt";
	
	public static IAlgorithm ALGORITHM = new ThiefSingleObjectiveEvolutionaryAsSetAlgorithm();
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> singleEAFrame = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		singleEAFrame
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory()))
			.set("crossover", new TTPCrossover(new NoCrossover<>(), new HalfUniformCrossover<>()))
			.set("mutation", new TTPMutation(new SwapMutation<>(), new BitFlipMutation()))
			.set("name", "EA-HUX");
		ALGORITHM = singleEAFrame.build();
	
	
		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader().read(PROBLEM);
		NonDominatedSolutionSet set = ALGORITHM.run(p, new Evaluator(500000), new MyRandom(123456));

		System.out.println(p);
		System.out.println(set.size());
		System.out.println(set);
		System.out.println(Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));

	}

	
}
