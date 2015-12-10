package com.msu.thief;

import org.apache.log4j.BasicConfigurator;

import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.ThiefProblem;
import com.msu.util.FileCollectorParser;

public class FixedTourProblemAnalyser {

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/json", "*", new JsonThiefProblemReader());
		/*
		for (ThiefProblem problem : fcp.collect()) {
	
			Builder<SingleObjectiveEvolutionaryAlgorithm> algorithm = new Builder<>(
					SingleObjectiveEvolutionaryAlgorithm.class);
			algorithm.set("populationSize", 50).set("probMutation", 0.3).set("factory", new OptimalPackingListFactory())
					.set("crossover", new UniformCrossover<>()).set("mutation", new BitFlipMutation());

			Tour<?> tour = AlgorithmUtil.calcBestTour(problem);

			Solution s = algorithm.build().run__(new ThiefProblemWithFixedTour(problem, tour),
					new Evaluator(500000), new MyRandom(123456));

			
			PackingList<?> var = (PackingList<?>) s.getVariable();
			
			StandardTimeEvaluator t = new StandardTimeEvaluator(problem);
			double complTime = t.evaluate(Pair.create(tour, var));
			List<Double> times = t.times;
			
			
			PackingList<?> b = (PackingList<?>) s.getVariable();
			for (Integer idx : b.toIndexSet()) {
				int city = problem.getItemCollection().getCityOfItem(idx);
				int idxOfCity = tour.encode().indexOf(city);
				double time = times.get(idxOfCity);
				System.out.println(time / times.get(times.size()-1));
			}
			System.out.println(String.format("Time complete: %s", complTime));
			System.out.println("-------------------");

		}
*/
	}

}
