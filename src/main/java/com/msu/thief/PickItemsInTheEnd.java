package com.msu.thief;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.plaf.synth.SynthSpinnerUI;

import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.ea.factory.PackRandomFactory;
import com.msu.thief.ea.factory.TourRandomFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class PickItemsInTheEnd {

	public static void main(String[] args) {

		final int runs = 3;
		final MyRandom r = new MyRandom(123456);
		final String file = "../thief-benchmark/json/thief-100-1-s.json";

		// SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new
		// ThiefSingleTSPLIBProblemReader().read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");

		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) new JsonThiefProblemReader().read(file);
		thief.setProfitEvaluator(new ExponentialProfitEvaluator(0.95, 1000));
		
		Tour pi = AlgorithmUtil.calcBestTour(thief);
		Pack z = AlgorithmUtil.calcBestPackingPlan(thief.getItems(), thief.getMaxWeight());
		

		System.out.println(thief.evaluate(TTPVariable.create(pi.getSymmetric(), z)));
		
		if (true) return;
		
		
		try {
			FileWriter writer = new FileWriter(new File("pick.csv"));

			
			
			// create random pi
			for (int i = 0; i < runs; i++) {

				Tour t = new TourRandomFactory(thief).next(r);
				// Tour t = AlgorithmUtil.calcBestTour(thief);

				ThiefProblemWithFixedTour fixedTour = new ThiefProblemWithFixedTour(thief, t);

				Builder<SingleObjectiveEvolutionaryAlgorithm<Pack, ThiefProblemWithFixedTour>> b = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);

				b.set("populationSize", 200).set("probMutation", 0.3).set("verbose", false).set("factory", new PackRandomFactory(thief))
						.set("crossover", new PackUniformCrossover(thief)).set("mutation", new PackBitflipMutation(thief));

				Solution<Pack> s = b.build().run(fixedTour, new StandardEvaluator(20000), r);

				System.out.println(s);
				
				TourInformation info = new StandardTimeEvaluator().evaluate_(thief, t, s.getVariable());

				for (int j = 0; j < thief.numOfCities(); j++) {
					double time = info.getTimeAtCities().get(j);
					String line = String.format("%s,%s\n", time / info.getTime(), info.getWeightAtCities().get(j));
					//System.out.print(line);
					writer.write(line);

				}

				// System.out.println(String.format("%s,%s", 1 ,
				// info.getWeightAtCities().));

			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

}
