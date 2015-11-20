package com.msu.thief.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.selection.RandomSelection;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.APackingPlanFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class ThiefSingleObjectiveEvolutionaryAlgorithm extends ASingleObjectiveAlgorithm {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;

	// ! factory for creating new instances
	protected APackingPlanFactory factory;

	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {

		//Report report = new Report(String.format("../ttp-benchmark/ttp-pi-new/%s_crossover_evo.csv", p));

		ThiefProblem problem = (ThiefProblem) p;

		// initialize random population
		SolutionSet population1 = new SolutionSet(populationSize);
		SolutionSet population2 = new SolutionSet(populationSize);
		
		Tour<?> bestTour = AlgorithmUtil.calcBestTour(problem);
		
		for (int i = 0; i < populationSize / 2; i++) {
			population1.add(evaluator.evaluate(problem, new TTPVariable(bestTour, (PackingList<?>) factory.next(problem, rand))));
			population2.add(evaluator.evaluate(problem, new TTPVariable(bestTour.getSymmetric(), (PackingList<?>) factory.next(problem, rand))));
		}
		

		//sortBySingleObjective(population);

		while (evaluator.hasNext()) {
			population1 = next(population1, rand, evaluator, problem);
			population2 = next(population2, rand, evaluator, problem);
		}

		/*
		 * if (generation == 100) { for (Solution s : population) {
		 * System.out.println(String.format("%s", s)); }
		 * System.out.println("-----------------------------"); }
		 */
		
		if (population1.get(0).getObjectives(0) < population2.get(0).getObjectives(0)) return population1.get(0);
		else return population2.get(0);

	}

	public void sortBySingleObjective(SolutionSet set) {
		set.sort(new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				return Double.compare(o1.getObjectives(0), o2.getObjectives(0));
			}
		});
	}

	protected SolutionSet next(SolutionSet population, MyRandom rand, IEvaluator evaluator, IProblem problem) {
		// mating with random selection of the best 20 percent
		SolutionSet offsprings = new SolutionSet(populationSize);

		RandomSelection selector = new RandomSelection(population, rand);
		while (offsprings.size() < populationSize / 2) {

			Solution sol1 = selector.next();
			Solution sol2 = selector.next();

			TTPVariable p1 = sol1.getVariable().cast(TTPVariable.class);
			TTPVariable p2 = sol2.getVariable().cast(TTPVariable.class);

			List<Boolean> b1 = p1.getPackingList().get();
			List<Boolean> b2 = p2.getPackingList().get();

			List<Boolean> c1 = new ArrayList<>();
			List<Boolean> c2 = new ArrayList<>();

			for (int i = 0; i < b1.size(); i++) {
				if (rand.nextDouble() < 0.5) {
					c1.add(b1.get(i));
					c2.add(b2.get(i));
				} else {
					c1.add(b2.get(i));
					c2.add(b1.get(i));
				}
			}
			/*
			 * Solution solChild1 = problem.evaluate(new
			 * TTPVariable(p1.getTour(), new BooleanPackingList(c1))); Solution
			 * solChild2 = problem.evaluate(new TTPVariable(p2.getTour(), new
			 * BooleanPackingList(c2)));
			 * 
			 * report.write(String.format("%s,%s,%s,%s,%s\n", generation,
			 * solChild1.getObjectives(0), sol1.getObjectives(0),
			 * solChild1.getObjectives(0) < sol1.getObjectives(0),
			 * b1.equals(b2))); report.write(String.format("%s,%s,%s,%s,%s\n",
			 * generation, solChild2.getObjectives(0), sol2.getObjectives(0),
			 * solChild2.getObjectives(0) < sol2.getObjectives(0),
			 * b1.equals(b2)));
			 */
			if (rand.nextDouble() < probMutation) {

				List<Boolean> result1 = new ArrayList<>(c1);
				List<Boolean> result2 = new ArrayList<>(c2);

				final double prob = 1 / (double) result1.size();
				
				for (int i = 0; i < result1.size(); i++) {
					if (rand.nextDouble() < prob) result1.set(i, !result1.get(i));
				}
				
				for (int i = 0; i < result2.size(); i++) {
					if (rand.nextDouble() < prob) result2.set(i, !result2.get(i));
				}
				
				offsprings.add(
						evaluator.evaluate(problem, new TTPVariable(p1.getTour(), new BooleanPackingList(result1))));
				offsprings.add(
						evaluator.evaluate(problem, new TTPVariable(p2.getTour(), new BooleanPackingList(result2))));

			} else {
				offsprings.add(evaluator.evaluate(problem, new TTPVariable(p1.getTour(), new BooleanPackingList(c1))));
				offsprings.add(evaluator.evaluate(problem, new TTPVariable(p2.getTour(), new BooleanPackingList(c2))));
			}

		}
		
		population.addAll(offsprings);
		// truncate the population -> survival of the fittest
		sortBySingleObjective(population);
		return new SolutionSet(population.subList(0, populationSize));
		

	}

}
