package com.msu.thief.algorithms;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.bilevel.tour.BiLevelEvoluationaryAlgorithm;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedPacking;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.FixedPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.FixedTourFactory;
import com.msu.util.MyRandom;

public class IterativePoolingEvolution extends ASingleObjectiveAlgorithm {

	
	
	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {
		
		SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) p;
		BiLevelEvoluationaryAlgorithm algorithm = new BiLevelEvoluationaryAlgorithm();
		Evaluator start = evaluator.createChildEvaluator(evaluator.getMaxEvaluations() / 10);
	    Solution s = algorithm.run__(problem, start, rand);
	    
	    TTPVariable var = (TTPVariable) s.getVariable();
		Tour<?> tour = var.getTour();
		PackingList<?> pack = var.getPackingList();
		 
		while (evaluator.hasNext()) {
			
			SingleObjectiveEvolutionaryAlgorithm algorithmPack = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new FixedPackingListFactory(pack))
				.set("crossover", new HalfUniformCrossover<>())
				.set("mutation", new BitFlipMutation()).build();
			
			Evaluator packEval = evaluator.createChildEvaluator(evaluator.getMaxEvaluations() / 10);
		    s = algorithmPack.run__(new ThiefProblemWithFixedTour(problem, tour), packEval, rand);
		    pack = (PackingList<?>) s.getVariable();
		    
		    
		    SingleObjectiveEvolutionaryAlgorithm algorithmTour = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
				.set("populationSize", 50)
				.set("probMutation", 0.3)
				.set("factory", new FixedTourFactory(tour))
				.set("crossover", new NoCrossover<>())
				.set("mutation", new SwapMutation<>()).build();
		    
		    
			Evaluator tourEval = evaluator.createChildEvaluator(evaluator.getMaxEvaluations() / 10);
		    s = algorithmTour.run__(new ThiefProblemWithFixedPacking(problem, pack), tourEval, rand);
		    
		    tour = (Tour<?>) s.getVariable();
		    
/*		    
		    for(Solution entry : algorithmPack.getPopulation().subList(0, 5)) {
		    	System.out.println(entry);
		    }
		    System.out.println();
		    for(Solution entry : algorithmTour.getPopulation().subList(0, 5)) {
		    	System.out.println(entry);
		    }
		    
		    System.out.println(pack);
		    System.out.println(tour);
		    System.out.println("-------------------------------");
			*/
		    
		}
		
	
		return evaluator.evaluate(problem, new TTPVariable(tour,pack));
	}

	

	
}
