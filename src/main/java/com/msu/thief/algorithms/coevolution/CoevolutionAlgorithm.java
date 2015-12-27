package com.msu.thief.algorithms.coevolution;

import java.util.HashSet;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.algorithms.coevolution.selector.ASelector;
import com.msu.thief.algorithms.coevolution.selector.NBestSelector;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedPacking;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class CoevolutionAlgorithm extends ASingleObjectiveAlgorithm {

	
	protected ASelector selector = new NBestSelector(1);
	
	protected int numOfGenerations = 5;
	
	
	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {

		// select the best tour for the start
		SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) p;

	    Tour<?> tour = AlgorithmUtil.calcBestTour(problem);
	    PackingList<?> pack = AlgorithmUtil.calcBestPackingPlan(problem);
			 

		// the two subpopulations
		SolutionSet populationPack = new SolutionSet();
		for (int i = 0; i < 50; i++) {
			populationPack.add(evaluator.evaluate(new ThiefProblemWithFixedTour(problem, tour), new OptimalPackingListFactory().next(problem, rand)));
		}
		populationPack = new SolutionSet(new HashSet<>(populationPack));
		SingleObjectiveEvolutionaryAlgorithm.sortBySingleObjective(populationPack);
		
		
		SolutionSet populationTSP = new SolutionSet();
		populationTSP.add(evaluator.evaluate(new ThiefProblemWithFixedPacking(problem, pack), tour));
		populationTSP.add(evaluator.evaluate(new ThiefProblemWithFixedPacking(problem, pack), tour.getSymmetric()));
		SingleObjectiveEvolutionaryAlgorithm.sortBySingleObjective(populationTSP);
		
		
		// create an evaluator for the coevolutionary approach
		CoevoluationCollaborativeEvaluator coevoEvaluator = new CoevoluationCollaborativeEvaluator( evaluator.getMaxEvaluations(), selector, rand);
		
		
		//set collaborators for the initial population
		coevoEvaluator.setCollaboratingTours(populationTSP);


		while (coevoEvaluator.hasNext()) {


			// evolution of the packing lists on collaborating tours
			
			SingleObjectiveEvolutionaryAlgorithm packEvolution = new Builder<SingleObjectiveEvolutionaryAlgorithm>(
					SingleObjectiveEvolutionaryAlgorithm.class)
						.set("populationSize", 50)
						.set("probMutation", 0.3)
						.set("crossover", new HalfUniformCrossover<>())
						.set("mutation", new BitFlipMutation())
						.build();
			

			packEvolution.setPopulation(populationPack);
			for (int i = 0; i < numOfGenerations; i++) packEvolution.next(problem, coevoEvaluator, rand);

			populationPack = packEvolution.getPopulation();
			coevoEvaluator.setCollaboratingPackingLists(populationPack);
			

			
			// evolution of the tour collaborating with packing lists
			
			SingleObjectiveEvolutionaryAlgorithm tourEvolution = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
					.set("populationSize", 50)
					.set("probMutation", 1.0)
					.set("crossover", new NoCrossover<>())
					.set("mutation", new SwapMutation<>())
					.build();
			

			tourEvolution.setPopulation(populationTSP);
			for (int i = 0; i < numOfGenerations; i++) tourEvolution.next(problem, coevoEvaluator, rand);
			
			populationTSP = tourEvolution.getPopulation();
			coevoEvaluator.setCollaboratingTours(populationTSP);
			
	
			

		}
		
		return evaluator.evaluate(problem, coevoEvaluator.best);
	}



}
