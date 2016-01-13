package com.msu.thief.algorithms.tour;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.thief.algorithms.AFixedTourAlgorithm;
import com.msu.thief.ea.pack.crossover.ThiefUniformCrossover;
import com.msu.thief.ea.pack.factory.ThiefPackOneItemFactory;
import com.msu.thief.ea.pack.mutation.ThiefBitflipMutation;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.util.MyRandom;



public class EvolutionOnRelevantItems extends AFixedTourAlgorithm {

	//! number when it counts as converged - no improvement for n generations
	public final int CONVERGENCE_GENERATIONS = 30;

	
	@Override
	public Solution run_(FixedTourSingleObjectiveThiefProblem thief, IEvaluator eval, MyRandom rand) {
		
		Solution s = new SolveKnapsackWithHeuristicValues().run_(thief, eval, rand);
		Pack p = ((TTPVariable) s.getVariable()).getPack();
		
		Builder<ThiefSingleObjectiveEvolutionaryFixedTour> b = new Builder<>(ThiefSingleObjectiveEvolutionaryFixedTour.class);
		b
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("crossover", new ThiefUniformCrossover())
			.set("mutation", new ThiefBitflipMutation());
		
		SolutionSet initial = new SolutionSet();
		
		ThiefPackOneItemFactory fac =  new ThiefPackOneItemFactory();
		fac.initialize(thief.getProblem(), rand, p.encode());
		
		while(fac.hasNext()) {
			initial.add(eval.evaluate(thief, fac.create()));
		}
		ThiefSingleObjectiveEvolutionaryFixedTour ea = b.build();
		ea.setPopulation(initial);
		
		Solution best = null;
		int counter = 0;
		
		do {
			ea.next(thief, eval, rand);
			Solution next = ea.getPopulation().get(0);
			
			// if we found a new solution
			if (best == null || new SolutionDominator().isDominating(next, best)) {
				best = next;
				counter = -1;
			}
			counter++;
		} while (counter < CONVERGENCE_GENERATIONS);
		
		return eval.evaluate(thief.getProblem(), TTPVariable.create(thief.getTour(), (Pack) best.getVariable()));
	}

	


}
