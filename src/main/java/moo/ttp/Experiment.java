package moo.ttp;

import java.io.IOException;

import moo.ttp.moea.MOEAProblem;

import org.moeaframework.Analyzer;
import org.moeaframework.Executor;

public class Experiment {
	public static void main(String[] args) {

		Analyzer analyzer = new Analyzer()
				.withProblemClass(MOEAProblem.class)
				.includeAllMetrics()
				.showStatisticalSignificance();

		Executor executor = new Executor()
			.withProblemClass(MOEAProblem.class)
			.withMaxEvaluations(10000)
			.withProperty("operator", "ttp");

		analyzer.addAll("NSGAII", executor.withAlgorithm("NSGAII").runSeeds(50));
		analyzer.addAll("MOEAD", executor.withAlgorithm("MOEAD").runSeeds(50));
		analyzer.addAll("Random", executor.withAlgorithm("Random").runSeeds(50));
		analyzer.addAll("GDE3", executor.withAlgorithm("GDE3").runSeeds(50));
		
		
		try {
			analyzer.printAnalysis();
		} catch (IOException e) {
			e.printStackTrace();
		}
		


		// display the results
		/*
		System.out.format("Objective1  Objective2%n");

		for (Solution solution : result) {
			System.out.format("%.4f      %.4f%n", solution.getObjective(0), solution.getObjective(1));
		}
		*/

	}
}
