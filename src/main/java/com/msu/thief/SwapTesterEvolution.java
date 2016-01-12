package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.crossover.permutation.OrderedCrossover;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.bilevel.tour.SolveKnapsackWithHeuristicValues;
import com.msu.thief.algorithms.heuristic.TourHeuristicProblem;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;
import com.msu.util.MyRandom;

public class SwapTesterEvolution {

	public static void main(String[] args) {

		BasicConfigurator.configure();

		Builder<SingleObjectiveEvolutionaryAlgorithm> builder = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		builder
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("crossover", new OrderedCrossover<>())
			.set("mutation", new SwapMutation<>())
			.set("factory", new OptimalTourFactory())
			.set("name", "NSGAII-TOUR-HEUR");

		SingleObjectiveEvolutionaryAlgorithm soea = builder.build();
/*
		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader()
				.read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");
		*/
		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new BonyadiSingleObjectiveReader()
				.read("../ttp-benchmark/SingleObjective/10/10_5_6_25.txt");

		MyRandom rand = new MyRandom(123456);

		IEvaluator eval = new Evaluator(250000);
		Solution s = soea.run__(new TourHeuristicProblem(thief, eval), eval, rand);

		
		Tour<?> tour = (Tour<?>) s.getVariable();
		Solution local = new SolveKnapsackWithHeuristicValues().run___(new ThiefProblemWithFixedTour(thief, tour), new Evaluator(250000), rand);
		PackingList<?> pack = (PackingList<?>) local.getVariable();
		
		Solution test = new OnePlusOneEAFixedTour(pack).run___(new ThiefProblemWithFixedTour(thief, tour),
				new Evaluator(250000), rand);

		System.out.println(s);
		System.out.println(test);
		System.out.println("----------------");

		System.out.println("FINISHED");

	}

}
