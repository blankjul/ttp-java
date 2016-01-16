package com.msu.thief.algorithms.impl.tour;

import com.msu.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.interfaces.IFixedTourAlgorithm;
import com.msu.thief.ea.factory.ThiefPackOneItemFactory;
import com.msu.thief.ea.operators.ThiefBitflipMutation;
import com.msu.thief.ea.operators.ThiefUniformCrossover;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.util.MyRandom;

public class FixedTourEvolutionOnRelevantItems implements IFixedTourAlgorithm {

	// ! number when it counts as converged - no improvement for n generations
	public final int CONVERGENCE_GENERATIONS = 30;

	@Override
	public Solution<Pack> run(ThiefProblemWithFixedTour problem, IEvaluator evaluator, MyRandom rand) {
		
		final AbstractThiefProblem thief = problem.getProblem();
		
		// create the evolutionary algorithm
		FixedTourKnapsackWithHeuristic knp = new Builder<FixedTourKnapsackWithHeuristic>(FixedTourKnapsackWithHeuristic.class)
				.set("postPruneItems", false)
				.build();
		
		Solution<Pack> s = knp.run(problem, evaluator, rand);
		Pack p = s.getVariable();
		
		
		// create the evolutionary algorithm
		Builder<SingleObjectiveEvolutionaryAlgorithm<Pack, ThiefProblemWithFixedTour>> b = 
				new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		
		b
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("crossover", new ThiefUniformCrossover(thief))
			.set("mutation", new ThiefBitflipMutation(thief));
		SingleObjectiveEvolutionaryAlgorithm<Pack, ThiefProblemWithFixedTour> ea = b.build();
		
		
		// create the initial population
		ThiefPackOneItemFactory fac =  new ThiefPackOneItemFactory(thief, p.decode());
		SolutionSet<Pack> population = new SolutionSet<Pack>();
		while(fac.hasNext()) {
			population.add(evaluator.evaluate(problem, fac.next(rand)));
		}
		
		
		// iterate until converged
		
		Solution<Pack> best = null;
		int counter = 0;
		
		do {
			population = ea.next(problem, evaluator, rand, population);
			Solution<Pack> next = population.get(0);
			
			// if we found a new solution
			if (best == null || new SolutionDominator().isDominating(next, best)) {
				best = next;
				counter = -1;
			}
			counter++;
		} while (counter < CONVERGENCE_GENERATIONS);
		
		return best;
		
	}

}
