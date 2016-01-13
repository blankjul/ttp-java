package com.msu.thief.algorithms.tour;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.ea.pack.crossover.PackCrossover;
import com.msu.thief.ea.pack.crossover.ThiefUniformCrossover;
import com.msu.thief.ea.pack.factory.PackFactory;
import com.msu.thief.ea.pack.mutation.PackMutation;
import com.msu.thief.ea.pack.mutation.ThiefBitflipMutation;
import com.msu.thief.problems.variable.Pack;
import com.msu.util.MyRandom;

public class ThiefSingleObjectiveEvolutionaryFixedTour extends ASingleObjectiveAlgorithm {

	// ! size of the whole Population
	protected int populationSize = 50;

	// ! default mutation probability
	protected Double probMutation = 0.3;

	// ! operator for crossover
	protected PackCrossover crossover = new ThiefUniformCrossover();

	// ! operator for mutation
	protected PackMutation mutation = new ThiefBitflipMutation();

	// ! factory for creating new instances
	protected PackFactory factory;

	// ! population of the last run
	protected SolutionSet population;



	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {
		
		FixedTourSingleObjectiveThiefProblem thief = (FixedTourSingleObjectiveThiefProblem) p;
		
		// initialize random population
		SolutionSet initial = new SolutionSet(populationSize * 2);
		factory.initialize(thief.getProblem(), rand);
		
		for (int i = 0; i < populationSize && factory.hasNext(); i++) {
			initial.add(evaluator.evaluate(thief, factory.create()));
		}
		
		return run__(thief, evaluator, rand, initial);
	}

	
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand, SolutionSet initial) {
		FixedTourSingleObjectiveThiefProblem thief = (FixedTourSingleObjectiveThiefProblem) p;
		this.population = initial;
		while (evaluator.hasNext()) {
			next(thief, evaluator, rand);
		}
		return population.get(0);
	}


	public void next(FixedTourSingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {

		// mating with random selection of the best 20 percent
		SolutionSet offsprings = new SolutionSet(populationSize);

		// selects per default always the maximal value
		BinaryTournamentSelection selector = new BinaryTournamentSelection(population, new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				return -1 * SingleObjectiveEvolutionaryAlgorithm.comp.compare(o1, o2);
			}
		}, rand);
		

		while (offsprings.size() < populationSize) {
			// crossover
			
			Pack p1 = (Pack) selector.next().getVariable();
			Pack p2 = (Pack) selector.next().getVariable();
			
			List<Pack> nextOffsprings = crossover.crossover(problem.getProblem(), rand, p1, p2);
			
			// mutation
			for (Pack off: nextOffsprings) {
				if (rand.nextDouble() < this.probMutation) {
					mutation.mutate(problem.getProblem(), rand, off);
				}
				offsprings.add(evaluator.evaluate(problem, off));
			}
		}
		
		population.addAll(offsprings);

		// eliminate duplicates to ensure variety in the population
		population = new SolutionSet(new HashSet<>(population));
		// truncate the population -> survival of the fittest
		Collections.sort(population, SingleObjectiveEvolutionaryAlgorithm.comp);
		population = new SolutionSet(population.subList(0, Math.min(population.size(), populationSize)));

		
		// print n best solutions
		//ThiefSingleObjectiveEvolutionaryAlgorithm.print(population, 5);
		//System.out.println("------------------------------");

		
	}


	public SolutionSet getPopulation() {
		return population;
	}
	
	public void setPopulation(SolutionSet population) {
		this.population = population;
	}


}
