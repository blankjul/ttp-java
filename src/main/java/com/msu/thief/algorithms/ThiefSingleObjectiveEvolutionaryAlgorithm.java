package com.msu.thief.algorithms;

import java.util.Comparator;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.selection.RandomSelection;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.APackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class ThiefSingleObjectiveEvolutionaryAlgorithm extends ASingleObjectiveAlgorithm {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;

	// ! factory for creating new instances
	protected APackingListFactory factory;
	
	protected AbstractCrossover<?> cross;

	
	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {

		ThiefProblem problem = (ThiefProblem) p;

		// initialize random population
		SolutionSet population1 = new SolutionSet(populationSize);
		SolutionSet population2 = new SolutionSet(populationSize);

		Tour<?> bestTour = AlgorithmUtil.calcBestTour(problem);

		for (int i = 0; i < populationSize / 2; i++) {
			population1.add(evaluator.evaluate(problem,
					new TTPVariable(bestTour, (PackingList<?>) factory.next(problem, rand))));
			population2.add(evaluator.evaluate(problem,new TTPVariable(bestTour.getSymmetric(), (PackingList<?>) factory.next(problem, rand))));
		}


		while (evaluator.hasNext()) {
			population1 = next(population1, rand, evaluator, problem, bestTour);
			population2 = next(population2, rand, evaluator, problem, bestTour.getSymmetric());
		}

		if (population1.get(0).getObjectives(0) < population2.get(0).getObjectives(0))
			return population1.get(0);
		else
			return population2.get(0);

	}

	protected SolutionSet next(SolutionSet population, MyRandom rand, IEvaluator evaluator, IProblem problem,
			Tour<?> tour) {
		// mating with random selection of the best 20 percent
		SolutionSet offsprings = new SolutionSet(populationSize);

		RandomSelection selector = new RandomSelection(population, rand);
		while (offsprings.size() < populationSize / 2) {

			PackingList<?> l1 = ((TTPVariable) selector.next().getVariable()).getPackingList();
			PackingList<?> l2 = ((TTPVariable) selector.next().getVariable()).getPackingList();

			List<IVariable> vars = cross.crossover(l1, l2, problem, rand);

			for (IVariable v : vars) {
				if (rand.nextDouble() < probMutation)
					v = new BitFlipMutation().mutate(v, problem, rand);
				
				offsprings.add(evaluator.evaluate(problem, new TTPVariable(tour, (PackingList<?>) v)));
			}
		}

		population.addAll(offsprings);

		// truncate the population -> survival of the fittest
		sortBySingleObjective(population);
		return new SolutionSet(population.subList(0, populationSize));

	}

	public void sortBySingleObjective(SolutionSet set) {
		set.sort(new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				return Double.compare(o1.getObjectives(0), o2.getObjectives(0));
			}
		});
	}

}
