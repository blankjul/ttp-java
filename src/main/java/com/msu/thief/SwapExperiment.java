package com.msu.thief;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.ea.factory.PackOptimalFactory;
import com.msu.thief.ea.factory.TourRandomFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.ea.operators.TourSimpleSwapMutation;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class SwapExperiment {

	public static Solution<Pack> calcPack(SingleObjectiveThiefProblem thief, Tour t, MyRandom r, int numofEvaluations) {

		ThiefProblemWithFixedTour fixedTour = new ThiefProblemWithFixedTour(thief, t);
		Builder<SingleObjectiveEvolutionaryAlgorithm<Pack, ThiefProblemWithFixedTour>> b = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);

		b.set("populationSize", 50)
		.set("probMutation", 0.3)
		.set("verbose", false)
		.set("factory", new PackOptimalFactory(thief))
		.set("crossover", new PackUniformCrossover(thief))
		.set("mutation", new PackBitflipMutation(thief));

		Solution<Pack> s = b.build().run(fixedTour, new StandardEvaluator(numofEvaluations), r);

		return s;

	}

	public static void main(String[] args) {

		final int numTours = 100;
		final int numRuns = 100000;
		final int numOfEvaluations = 500;
		final MyRandom r = new MyRandom(987654321);
		final String file = "../thief-benchmark/json/thief-50-1-l.json";


		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new ThiefSingleTSPLIBProblemReader().read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");

		// SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new JsonThiefProblemReader().read(file);

		try {
			FileWriter writer = new FileWriter(new File("/home/julesy/swap.csv"));

			writer.write("simple,tsp\n");

			for (int i = 0; i < numTours; i++) {

				Tour t = new TourRandomFactory(thief).next(r);
				Pack b = AlgorithmUtil.calcBestPackingPlan(thief.getItems(), thief.getMaxWeight());
				Solution<TTPVariable> parent = thief.evaluate(TTPVariable.create(t, b));
				
				
				for (int j = 0; j < numRuns; j++) {

					Tour simpleTour = t.copy();
					new TourSimpleSwapMutation().mutate(simpleTour, r);
					Solution<TTPVariable> offSimple = thief.evaluate(TTPVariable.create(simpleTour, b));

					Tour TSPTour = t.copy();
					new TourSwapMutation().mutate(TSPTour, r);
					Solution<TTPVariable> offTSP = thief.evaluate(TTPVariable.create(TSPTour, b));

					double dParent = -parent.getObjective(0);
					double dSimple = -offSimple.getObjective(0);
					double dTSP = -offTSP.getObjective(0);

					writer.write(String.format("%s,%s\n", (dSimple - dParent) / dParent, (dTSP - dParent) / dParent));

					
				}
				

			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
