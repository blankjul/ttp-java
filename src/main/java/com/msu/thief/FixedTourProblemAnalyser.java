package com.msu.thief;

import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.BasicConfigurator;

import com.msu.builder.Builder;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.FileCollectorParser;
import com.msu.util.MyRandom;

public class FixedTourProblemAnalyser {

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/json", "*", new JsonThiefProblemReader());
		
		/*FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/SingleObjective/10", "10_5_6_25.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/10", "10_10_2_50.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/10", "10_15_10_75.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/20", "20_5_6_75.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/20", "20_20_7_50.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/20", "20_30_9_25.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/50", "50_15_8_50.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/50", "50_25_3_75.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/50", "50_75_6_25.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100", "100_5_10_50.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100", "100_50_5_75.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100", "100_150_10_25.txt", new BonyadiSingleObjectiveReader());
		*/
		
		for (ThiefProblem problem : fcp.collect()) {
	
			Builder<SingleObjectiveEvolutionaryAlgorithm> algorithm = new Builder<>(
					SingleObjectiveEvolutionaryAlgorithm.class);
			algorithm.set("populationSize", 50).set("probMutation", 0.3).set("factory", new OptimalPackingListFactory())
					.set("crossover", new UniformCrossover<>()).set("mutation", new BitFlipMutation());

			Tour<?> tour = AlgorithmUtil.calcBestTour(problem);

			Solution s = algorithm.build().run__(new ThiefProblemWithFixedTour(problem, tour),
					new Evaluator(500000), new MyRandom(123456));

			
			PackingList<?> pack = (PackingList<?>) s.getVariable();
			
			TourInformation info =  new StandardTimeEvaluator().evaluate_(problem, tour, pack);
			
			ItemCollection<Item> collection = problem.getItemCollection();
			
			System.out.println(problem);
			System.out.println(tour);
			PackingList<?> b = (PackingList<?>) s.getVariable();
			for (Integer idx : new TreeSet<>(b.toIndexSet())) {
				Map<Integer, Integer> m = tour.getAsHash();
				int city = collection.getCityOfItem(idx);
				double time = info.getTimeAtCity(city);
				System.out.println(String.format("item: %s at city %s | %s | city: %s/%s | time: %.2f/%.2f", idx,collection.getCityOfItem(idx), problem.getItem(idx) , m.get(city), problem.numOfCities() - 1,time, info.getTime()));
			}
			System.out.println("-------------------");

		}

	}

}
