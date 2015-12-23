package com.msu.thief.algorithms.coevolution;

import java.util.HashSet;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.NoCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.mutation.NoMutation;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.OptimalTourFactory;
import com.msu.util.MyRandom;

public class CoevolutionAlgorithm extends ASingleObjectiveAlgorithm {

	final public int populationSize = 20;
	
	protected boolean mergeElementWise = false;

	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {

		// select the best tour for the start
		SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) p;
		
		// create the initial population
		SolutionSet population = new SolutionSet();
		TTPVariableFactory fac = new TTPVariableFactory(new OptimalTourFactory(), new OptimalPackingListFactory());
		for (IVariable variable : fac.next(problem, rand, populationSize)) {
			population.add(evaluator.evaluate(problem, variable));
		}

		// create packing evolutionary algorithm
		Builder<SingleObjectiveEvolutionaryAlgorithm> packBuilder = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
				.set("populationSize", populationSize)
				.set("probMutation", 0.3)
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new HalfUniformCrossover<>()))
				.set("mutation", new TTPMutation(new NoMutation<>(), new BitFlipMutation()));
		
		// create tour evolutionary algorithm
		Builder<SingleObjectiveEvolutionaryAlgorithm> tourBuilder = new Builder<SingleObjectiveEvolutionaryAlgorithm>(SingleObjectiveEvolutionaryAlgorithm.class)
				.set("populationSize", populationSize)
				.set("probMutation", 0.3)
				.set("crossover", new TTPCrossover(new NoCrossover<>(), new NoCrossover<>()))
				.set("mutation", new TTPMutation(new SwapMutation<>(), new NoMutation<>()));
		
		
		
		while (evaluator.hasNext()) {

			
			Evaluator pack = evaluator.createChildEvaluator(evaluator.getMaxEvaluations() / 20);
			SingleObjectiveEvolutionaryAlgorithm algoPack = packBuilder.build();
			algoPack.run__(problem, pack, rand, population);
			population = algoPack.getPopulation();

		
	
			
			if (!evaluator.hasNext()) break;
			population = (mergeElementWise) ? mergeEachElement(problem, evaluator, population) : mergeOnePool(problem, evaluator, population);
			population = new SolutionSet(new HashSet<>(population));
			SingleObjectiveEvolutionaryAlgorithm.sortBySingleObjective(population);
			population = new SolutionSet(population.subList(0, Math.min(population.size(), populationSize)));
			
			
			
			Evaluator tour = evaluator.createChildEvaluator(evaluator.getMaxEvaluations() / 20);
			SingleObjectiveEvolutionaryAlgorithm algoTour = tourBuilder.build();
			algoTour.run__(problem, tour, rand, population);
			population = algoTour.getPopulation();

			
			for (Solution s : population.subList(0, Math.min(population.size(), populationSize))) {
				System.out.println(s);
			}
			System.out.println("-----------------");
			
			
			if (!evaluator.hasNext()) break;
			population = (mergeElementWise) ? mergeEachElement(problem, evaluator, population) : mergeOnePool(problem, evaluator, population);
			population = new SolutionSet(new HashSet<>(population));
			SingleObjectiveEvolutionaryAlgorithm.sortBySingleObjective(population);
			population = new SolutionSet(population.subList(0, Math.min(population.size(), populationSize)));
			
			
	
			
		}
		
		SingleObjectiveEvolutionaryAlgorithm.sortBySingleObjective(population);
		return population.get(0);

	}
	
	
	protected SolutionSet mergeOnePool(IProblem problem, IEvaluator evaluator, SolutionSet population) {
		SolutionSet next = new SolutionSet();
		for (int i = 0; i < population.size(); i++) {
			for (int j = 0; j < population.size(); j++) {
				Tour<?> tour = ((TTPVariable) population.get(i).getVariable()).getTour();
				PackingList<?> packList = ((TTPVariable) population.get(j).getVariable()).getPackingList();
				next.add(evaluator.evaluate(problem, new TTPVariable(tour,packList)));
			}
		}
		return next;
	}
	
	
	protected SolutionSet mergeEachElement(IProblem problem, IEvaluator evaluator, SolutionSet population) {
		SolutionSet next = new SolutionSet();
		for (int i = 0; i < population.size(); i++) {
			Tour<?> tour = ((TTPVariable) population.get(i).getVariable()).getTour();
			SolutionSet poolForTour = new SolutionSet();
			for (int j = 0; j < population.size(); j++) {
				PackingList<?> packList = ((TTPVariable) population.get(j).getVariable()).getPackingList();
				poolForTour.add(evaluator.evaluate(problem, new TTPVariable(tour,packList)));
			}
			SingleObjectiveEvolutionaryAlgorithm.sortBySingleObjective(poolForTour);
			next.add(poolForTour.get(0));
		}
		return next;
	}

}
