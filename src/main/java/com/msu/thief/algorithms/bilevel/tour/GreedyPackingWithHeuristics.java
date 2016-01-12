package com.msu.thief.algorithms.bilevel.tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.heuristics.ItemAverageCaseHeuristic;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.util.ThiefUtil;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.util.MyRandom;
import com.msu.util.Util;


public class GreedyPackingWithHeuristics extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour> {

	


	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {

		Map<Integer, Double> heuristic = new ItemAverageCaseHeuristic(problem, eval)
				.calc(Util.createIndex(problem.numOfItems()));
		
		LinkedHashMap<Integer, Double> sorted = ThiefUtil.sortByValue(heuristic);
		List<Entry<Integer, Double>> s = new ArrayList<>(sorted.entrySet());
		Collections.reverse(s);

		double weight = 0;
		int idx = 0;
		
		SolutionSet population = new SolutionSet();
		
		while (idx < s.size()) {
			
			int item = s.get(idx).getKey();
			
			weight += problem.getItems().get(item).getWeight();
			
			//if (weight > problem.getMaxWeight()) break;
			
			BooleanPackingList b = new BooleanPackingList(problem.numOfItems());
			b.get().set(item, true);
			
			population.add(eval.evaluate(problem, b));
			
			idx++;
		}
		
		
		final SingleObjectiveEvolutionaryAlgorithm EA = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("population", population)
				.set("crossover", new UniformCrossover<>())
				.set("mutation", new BitFlipMutation())
				.build();
		
		
		
		while (eval.hasNext()) {
			EA.next(problem, eval, rand);
		}
		
		Solution best = EA.getPopulation().get(0);
		
		return best;
		
	}


}
