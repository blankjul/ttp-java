package com.msu.thief.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.interfaces.IVariableFactory;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.crossover.permutation.OrderedCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.selection.RandomSelection;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class KnapsackLocalSearch extends ASingleObjectiveAlgorithm {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;

	// ! factory for creating new instances
	protected IVariableFactory factory;
	
	
	
	// ! population of the last run
	protected SolutionSet population;
	
	
	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {

		ThiefProblemWithFixedTour problem = (ThiefProblemWithFixedTour) p;

		population = new SolutionSet(populationSize * 2);
		for (IVariable variable : factory.next(problem, rand, populationSize)) {
			population.add(evaluator.evaluate(problem, variable));
		}
		
	
		while (evaluator.hasNext()) {
			next(problem, evaluator, rand);
		}
		return population.get(0);

	}

	public void next(IProblem problem, IEvaluator evaluator, MyRandom rand) {
		
		
		SolutionSet offsprings = new SolutionSet(populationSize);

		RandomSelection selector = new RandomSelection(population, rand);
		while (offsprings.size() < populationSize / 2) {

			PackingList<?> l1 = (PackingList<?>) selector.next().getVariable();
			PackingList<?> l2 = (PackingList<?>) selector.next().getVariable();
			

			List<List<Boolean>> vars = new UniformCrossover<Boolean>().crossover_(l1.encode(), l2.encode(), problem, rand, evaluator);

			for (List<Boolean> v : vars) {
				List<Boolean> result = v;
				if (rand.nextDouble() < probMutation) v = new BitFlipMutation().mutate_(v, problem, rand, evaluator);
				offsprings.add(evaluator.evaluate(problem, new BooleanPackingList(result)));
			}
		}

		population.addAll(offsprings);

		// eliminate duplicates to ensure variety in the population
		population = new SolutionSet(new HashSet<>(population));
		// truncate the population -> survival of the fittest
		SingleObjectiveEvolutionaryAlgorithm.sortBySingleObjective(population);
		population = new SolutionSet(population.subList(0, Math.min(population.size(), populationSize)));

	}

}
