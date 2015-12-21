package com.msu.thief.algorithms;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.util.MyRandom;

public class SingleThiefEvoluation extends SingleObjectiveEvolutionaryAlgorithm{

	public Solution run__(IProblem problem, IEvaluator evaluator, MyRandom rand, SolutionSet initialPopulation) {
		
		this.population = initialPopulation;
		
		sortBySingleObjective(population);
		
		
		while (evaluator.hasNext()) {
			

			
			// mating with random selection of the best 20 percent
			SolutionSet offsprings = new SolutionSet(populationSize);
			
			// selects per default always the maximal value
			BinaryTournamentSelection selector = new BinaryTournamentSelection(population, new Comparator<Solution>() {
				@Override
				public int compare(Solution o1, Solution o2) {
					return -1 * comp.compare(o1, o2);
				}
			}, rand);
			
			
			while (offsprings.size() < populationSize) {
				// crossover
				List<IVariable> off = crossover.crossover(selector.next().getVariable(), selector.next().getVariable(), problem, rand);
				// mutation
				for (IVariable offspring : off) {
					if (rand.nextDouble() < this.probMutation) {
						offspring = mutation.mutate(offspring, problem, rand);
					}
					offsprings.add(evaluator.evaluate(problem, offspring));
				}
			}
			population.addAll(offsprings);

			// eliminate duplicates to ensure variety in the population
			population = new SolutionSet(new HashSet<>(population));
			// truncate the population -> survival of the fittest
			sortBySingleObjective(population);
			population = new SolutionSet(population.subList(0, Math.min(population.size(), populationSize)));
	
			
			
		}
		

				
		for (Solution s : population.subList(0, Math.min(population.size(), 100))) {
			System.out.println(String.format("%s %s", s, s.hashCode()));
		}
		System.out.println("---------------------------");
		
		
		
		return population.get(0);
	}
	
	
}
