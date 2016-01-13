package com.msu.thief.algorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.ea.TTPCrossover;
import com.msu.thief.ea.TTPMutation;
import com.msu.thief.ea.pack.crossover.UniformCrossover;
import com.msu.thief.ea.pack.factory.OptimalPackFactory;
import com.msu.thief.ea.pack.factory.PackFactory;
import com.msu.thief.ea.pack.mutation.BitflipMutation;
import com.msu.thief.ea.tour.crossover.OrderedCrossover;
import com.msu.thief.ea.tour.factory.OptimalTourFactory;
import com.msu.thief.ea.tour.factory.TourFactory;
import com.msu.thief.ea.tour.mutation.SwapMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.util.MyRandom;

public class ThiefSingleObjectiveEvolutionaryAlgorithm extends ASingleObjectiveAlgorithm {

	// ! size of the whole Population
	protected int populationSize = 50;

	// ! default mutation probability
	protected Double probMutation = 0.3;

	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {

		AbstractThiefProblem thief = (AbstractThiefProblem) p;

		// initialize random population
		SolutionSet population = new SolutionSet(populationSize);

		PackFactory facPack = new OptimalPackFactory(thief, rand);
		TourFactory facTour = new OptimalTourFactory(thief, rand);

		for (int i = 0; i < populationSize; i++) {
			TTPVariable var = TTPVariable.create(facTour.create(), facPack.create());
			Solution s = evaluator.evaluate(thief, var);
			population.add(s);
		}

		// selects per default always the maximal value
		BinaryTournamentSelection selector = new BinaryTournamentSelection(population, new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				return -1 * SingleObjectiveEvolutionaryAlgorithm.comp.compare(o1, o2);
			}
		}, rand);

		while (evaluator.hasNext()) {

			// mating with random selection of the best 20 percent
			SolutionSet offsprings = new SolutionSet(populationSize);

			while (offsprings.size() < populationSize) {

				// select the parents
				TTPVariable p1 = (TTPVariable) selector.next().getVariable();
				TTPVariable p2 = (TTPVariable) selector.next().getVariable();

				// do the crossover
				TTPCrossover crossover = new TTPCrossover(new OrderedCrossover(), new UniformCrossover());
				List<TTPVariable> nextOffsprings = crossover.crossover(p1, p2, thief, rand, evaluator);

				for (TTPVariable offpsring : nextOffsprings) {

					if (rand.nextDouble() < probMutation) {
						TTPMutation mutation = new TTPMutation(new SwapMutation(), new BitflipMutation());
						mutation.mutate(offpsring, thief, rand, evaluator);
					}

					Solution s = evaluator.evaluate(thief, offpsring);

					offsprings.add(s);
				}

			}

			population.addAll(offsprings);

			// eliminate duplicates to ensure variety in the population
			population = new SolutionSet(new HashSet<>(population));

			// sort and truncate the population -> survival of the fittest
			Collections.sort(population, SingleObjectiveEvolutionaryAlgorithm.comp);


			population = new SolutionSet(population.subList(0, Math.min(population.size(), populationSize)));

			// print n best solutions
			print(population, 5);

		}

		return population.get(0);

	}

	private void print(SolutionSet population, int n) {
		for (Solution solution : population.subList(0, Math.min(n, population.size()))) {
			System.out.println(solution);
		}
		System.out.println("------------------------------");
	}

}
