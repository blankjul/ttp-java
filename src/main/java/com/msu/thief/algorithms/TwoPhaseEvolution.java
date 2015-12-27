package com.msu.thief.algorithms;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.moo.model.solution.SolutionSet;
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
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class TwoPhaseEvolution extends ASingleObjectiveAlgorithm {

	//! how long in the beginning the bi-level optimization should work
	protected double bilevelEvaluationsFactor = 0.1;
	
	//! how long each pool should calculated generations
	protected double poolingEvaluationsFactor = 0.2;
	
	
	//! evolutionary algorithm to optimize the packing plan given a tour
	protected Builder<SingleObjectiveEvolutionaryAlgorithm> algorithmPack = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("crossover", new HalfUniformCrossover<>())
			.set("mutation", new BitFlipMutation());
	
	
	//! evolutionary algorithm to optimize the tour given a packing plan
	protected Builder<SingleObjectiveEvolutionaryAlgorithm> algorithmTour = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
			.set("populationSize", 50)
			.set("probMutation", 1.0)
			.set("crossover", new NoCrossover<>())
			.set("mutation", new SwapMutation<>());
	    
	
	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {
		
		SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) p;
		
		final int maxEvaluations = evaluator.getMaxEvaluations();

	    Solution s = null;
	    // use bi-level optimization  to get a the better starting tour
	    if (bilevelEvaluationsFactor > 0) {
	    	IEvaluator biEval = evaluator.createChildEvaluator((int) (maxEvaluations * bilevelEvaluationsFactor));
	    	s = getStartingSolutionBilevel(problem, biEval, rand);
	    } 
	    // use simple evaluations
	    else  s = getStartingSolutionSimple(problem, evaluator, rand);
	    		
	    
	    // the starting variables
	    TTPVariable var = (TTPVariable) s.getVariable();
		Tour<?> tour = var.getTour();
		PackingList<?> pack = var.getPackingList();
		 

		while (evaluator.hasNext()) {

			
			// solve the knapsack with best fixed tour
			ThiefProblemWithFixedTour fixedTourProblem = new ThiefProblemWithFixedTour(problem, tour);
			Evaluator packEval = evaluator.createChildEvaluator((int) (maxEvaluations * poolingEvaluationsFactor));
			
			// calculate the initial population mutate from the best PACKING plan found so far
			SolutionSet population = new SolutionSet();
			for (int i = 0; i < 50; i++) {
				population.add(evaluator.evaluate(fixedTourProblem, new BitFlipMutation().mutate(pack, null, rand)));
			}
			
			// use the algorithm to optimize the packing plan for a given tour - included in packProblem
		    s = algorithmPack.build().run__(fixedTourProblem, packEval, rand, population);
		    pack = (PackingList<?>) s.getVariable();
		    
		    // now the packing plan is fixed to the best
		    ThiefProblemWithFixedPacking fixedPackProblem = new ThiefProblemWithFixedPacking(problem, pack);
		    Evaluator tourEval = evaluator.createChildEvaluator((int) (maxEvaluations * poolingEvaluationsFactor));

			// calculate the initial population mutate from the best TOUR plan found so far
		    population = new SolutionSet();
			for (int i = 0; i < 50; i++) {
				population.add(evaluator.evaluate(fixedPackProblem, new SwapMutation<>().mutate(tour, null, rand)));
			}
			
			// use the algorithm to optimize the tour for a given packing plan
		    s = algorithmTour.build().run__(fixedPackProblem, tourEval, rand, population);
		    tour = (Tour<?>) s.getVariable();
		    
		    // fix the now for the next iteration

		}
		
		Solution best = problem.evaluate(new TTPVariable(tour,pack));
		
		return best;
	}

	
	
	public static Solution getStartingSolutionBilevel(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {
		BiLevelEvoluationaryAlgorithm algorithm = new BiLevelEvoluationaryAlgorithm();
	    Solution s = algorithm.run__(problem, evaluator, rand);
	    return s;
	}
	
	
	public static Solution getStartingSolutionSimple(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {
		Tour<?> tour = AlgorithmUtil.calcBestTour(problem);
		PackingList<?> pack = AlgorithmUtil.calcBestPackingPlan(problem);
	    
		Solution s1 = problem.evaluate(new TTPVariable(tour, pack));
		Solution s2 = problem.evaluate(new TTPVariable(tour.getSymmetric(), pack));
		Solution best = (new SolutionDominatorWithConstraints().isDominating(s1, s2)) ? s1 : s2;
	    return best;
	}
	
	
}
