package com.msu.thief.algorithms.impl;

import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.moo.operators.crossover.NoCrossover;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.factory.PackWithBitflipFactory;
import com.msu.thief.ea.factory.TourWithSwapFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedPack;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefTwoPhaseEvolution extends AThiefSingleObjectiveAlgorithm {


	@Override
	public Solution<com.msu.thief.problems.variable.TTPVariable> run(SingleObjectiveThiefProblem thief,
			IEvaluator evaluator, MyRandom rand) {
		
	
		final int maxEvaluations = evaluator.getMaxEvaluations();
		final double initialPoolingFactor = 0.3;
		final double poolingEvaluationsFactor = 0.05;
		
		SolutionDominatorWithConstraints dominator = new SolutionDominatorWithConstraints();
		
		// calculate a non dominated solution set for the starting
		Solution<TTPVariable> best = new ThiefBestOfMultiObjectiveFront().run(thief, evaluator.createChildEvaluator((int) (maxEvaluations * initialPoolingFactor))
				, rand);
		
		while (evaluator.hasNext()) {

			Tour nextTour = best.getVariable().getTour();
			Pack nextPack = best.getVariable().getPack();
			

			SingleObjectiveEvolutionaryAlgorithm<Pack, ThiefProblemWithFixedTour> algorithmPack = 
					new Builder<SingleObjectiveEvolutionaryAlgorithm<Pack, ThiefProblemWithFixedTour>>(SingleObjectiveEvolutionaryAlgorithm.class)
					.set("populationSize", 50)
					.set("probMutation", 0.3)
					.set("factory", new PackWithBitflipFactory(thief, nextPack))
					.set("crossover", new PackUniformCrossover(thief))
					.set("mutation", new PackBitflipMutation(thief))
				.build();
			
			nextPack = algorithmPack.run(new ThiefProblemWithFixedTour(thief, nextTour), evaluator.createChildEvaluator((int) (maxEvaluations * poolingEvaluationsFactor)), rand).getVariable();
			

			SingleObjectiveEvolutionaryAlgorithm<Tour, ThiefProblemWithFixedPack> algorithmTour = 
					new Builder<SingleObjectiveEvolutionaryAlgorithm<Tour, ThiefProblemWithFixedPack>>(SingleObjectiveEvolutionaryAlgorithm.class)
					.set("populationSize", 50)
					.set("probMutation", 1.0)
					.set("factory", new TourWithSwapFactory(nextTour))
					.set("crossover", new NoCrossover())
					.set("mutation", new TourSwapMutation())
				.build();
			
			
			nextTour = algorithmTour.run(new ThiefProblemWithFixedPack(thief, nextPack), evaluator.createChildEvaluator((int) (maxEvaluations * poolingEvaluationsFactor)), rand).getVariable();
			
			Solution<TTPVariable> next = thief.evaluate(TTPVariable.create(nextTour, nextPack));
			if (dominator.isDominating(next, best)){
				best = next;
			}
			
			
		}
		
		
		return best;
	}

	
	

	
}
