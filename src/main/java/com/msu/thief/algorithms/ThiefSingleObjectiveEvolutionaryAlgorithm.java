package com.msu.thief.algorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.tour.EvolutionOnRelevantItems;
import com.msu.thief.algorithms.tour.FixedTourSingleObjectiveThiefProblem;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.pack.crossover.ThiefUniformCrossover;
import com.msu.thief.ea.pack.factory.PackFactory;
import com.msu.thief.ea.pack.factory.ThiefOptimalPackFactory;
import com.msu.thief.ea.pack.mutation.ThiefBitflipMutation;
import com.msu.thief.ea.tour.crossover.ThiefOrderedCrossover;
import com.msu.thief.ea.tour.factory.ThiefOptimalTourFactory;
import com.msu.thief.ea.tour.factory.TourFactory;
import com.msu.thief.ea.tour.mutation.ThiefSwapMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;
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

		PackFactory facPack = new ThiefOptimalPackFactory();
		facPack.initialize(thief, rand);
		
		TourFactory facTour = new ThiefOptimalTourFactory();
		facTour.initialize(thief, rand);

		for (int i = 0; i < populationSize; i++) {
			Tour t = facTour.create();
			
			Solution opt = new EvolutionOnRelevantItems().run_(new FixedTourSingleObjectiveThiefProblem(thief, t),
					evaluator, rand);

			//TTPVariable var = TTPVariable.create(facTour.create(), facPack.create());
			//Solution s = evaluator.evaluate(thief, var);
			
			population.add(opt);
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
				ThiefCrossover crossover = new ThiefCrossover(new ThiefOrderedCrossover(), new ThiefUniformCrossover());
				List<TTPVariable> nextOffsprings = crossover.crossover(p1, p2, thief, rand, evaluator);
	
				for (TTPVariable offpsring : nextOffsprings) {

					if (rand.nextDouble() < probMutation) {
						ThiefMutation mutation = new ThiefMutation(new ThiefSwapMutation(), new ThiefBitflipMutation());
						mutation.mutate(offpsring, thief, rand, evaluator);
					}

					Solution s = evaluator.evaluate(thief, offpsring);

					if (rand.nextDouble() < 1 / (double) populationSize) {
						
						Tour localTour = ((TTPVariable) s.getVariable()).getTour();
						//Pack localPack = ((TTPVariable) s.getVariable()).getPack();

						Solution opt = new EvolutionOnRelevantItems().run_(new FixedTourSingleObjectiveThiefProblem(thief, localTour),
								evaluator, rand);
						
						if (new SolutionDominatorWithConstraints().isDominating(opt, s)) {
							s = opt;
							// System.out.println("--> ");
						}
						
					}

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
			System.out.println("------------------------------");

		}

		return population.get(0);

	}

	public static void print(SolutionSet population, int n) {
		for (Solution solution : population.subList(0, Math.min(n, population.size()))) {
			System.out.println(solution);
		}
	}

}
