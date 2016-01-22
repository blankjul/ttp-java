package com.msu.thief.algorithms.impl;

import java.util.HashSet;
import java.util.List;

import com.msu.moo.algorithms.single.SingleObjectiveBinaryTournament;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.ASelection;
import com.msu.moo.model.evaluator.ConvergenceEvaluator;
import com.msu.moo.model.solution.SingleObjectiveComparator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.bilevel.tour.FixedTourOnePlusOneEAHeuristicItems;
import com.msu.thief.algorithms.impl.subproblems.TourTwoOptimal;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.factory.PackOptimalFactory;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.ea.operators.TourOrderedCrossover;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefSteadyStateEvolution extends AThiefSingleObjectiveAlgorithm {

	// ! size of the whole Population
	protected int populationSize = 50;

	// ! default mutation probability
	protected Double probMutation = 0.3;
	

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem thief, IEvaluator evaluator, MyRandom rand) {

		ThiefFactory factory = new ThiefFactory(new TourOptimalFactory(thief), new PackOptimalFactory(thief));
		ThiefCrossover crossover = new ThiefCrossover(new TourOrderedCrossover(), new PackUniformCrossover(thief));
		ThiefMutation mutation = new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(thief));

		// initialize the population
		SolutionSet<TTPVariable> population = new SolutionSet<>();
		while (population.size() < populationSize) {
			population.add(evaluator.evaluate(thief, factory.next(rand)));
		}
		
		
		while (evaluator.hasNext()) {

			// selects per default always the maximal value
			ASelection<TTPVariable> selector = new SingleObjectiveBinaryTournament<>(population, rand);

			// crossover
			Solution<TTPVariable> p1 = selector.next();
			Solution<TTPVariable> p2 = selector.next();
			
			List<TTPVariable> off = crossover.crossover(p1.getVariable(), p2.getVariable(),
					rand);

			// mutation
			for (TTPVariable offspring : off) {

				if (rand.nextDouble() < this.probMutation) {
					mutation.mutate(offspring, rand);
				}

				// evaluate directly or perform local optimization
				Solution<TTPVariable> s = evaluator.evaluate(thief, offspring);
				
				if (rand.nextDouble() < 1 / (double) populationSize) {
					Tour t = TourTwoOptimal.optimize(thief, s.getVariable().getTour());
					Solution<Pack> localPack = new FixedTourOnePlusOneEAHeuristicItems().run(new ThiefProblemWithFixedTour(thief, t), new ConvergenceEvaluator(100, 1), rand);
					s =  thief.evaluate(TTPVariable.create(t, localPack.getVariable()));
				} 
				
				population.add(s);
			}

			// eliminate duplicates to ensure variety in the population
			SolutionSet<TTPVariable> next = new SolutionSet<TTPVariable>(new HashSet<>(population));
			// truncate the population -> survival of the fittest
			next.sort(new SingleObjectiveComparator());
			next = new SolutionSet<TTPVariable>(next.subList(0, Math.min(next.size(), populationSize)));

			population = next;

			evaluator.notify(population);
			
			//SingleObjectiveEvolutionaryAlgorithm.print(population, 3);
		}
		
		
		return population.get(0);
	}

}
