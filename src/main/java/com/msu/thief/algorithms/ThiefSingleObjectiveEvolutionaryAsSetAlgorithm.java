package com.msu.thief.algorithms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.operators.selection.RandomSelection;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.thief.algorithms.recombinations.LocalOptimaCrossover;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.APackingListFactory;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class ThiefSingleObjectiveEvolutionaryAsSetAlgorithm extends ASingleObjectiveAlgorithm {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;


	
	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {

		SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) p;
		Tour<?> bestTour = AlgorithmUtil.calcBestTour(problem);
		APackingListFactory factory = new OptimalPackingListFactory();
		
		SingleObjectiveThiefProblemWithFixedTour p1 = new SingleObjectiveThiefProblemWithFixedTour(problem, bestTour);
		
		SingleObjectiveThiefProblemWithFixedTour p2 = new SingleObjectiveThiefProblemWithFixedTour(problem, bestTour.getSymmetric());
		
		// initialize random population
		SolutionSet population1 = new SolutionSet(populationSize);
		SolutionSet population2 = new SolutionSet(populationSize);


		for (int i = 0; i < populationSize / 2; i++) {
			population1.add(evaluator.evaluate(p1, factory.next(p1,  rand)));
			population2.add(evaluator.evaluate(p2, factory.next(p2,  rand)));
		}


		while (evaluator.hasNext()) {
			population1 = next(population1, rand, evaluator, p1);
			population2 = next(population2, rand, evaluator, p2);
		}
		
		
		if (population1.get(0).getObjectives(0) < population2.get(0).getObjectives(0)) {
			PackingList<?> b = (PackingList<?>) population1.get(0).getVariable();
			return problem.evaluate(new TTPVariable(bestTour, b));
		}
		else {
			PackingList<?> b = (PackingList<?>) population2.get(0).getVariable();
			return problem.evaluate(new TTPVariable(bestTour.getSymmetric(), b));
		}

	}

	protected SolutionSet next(SolutionSet population, MyRandom rand, IEvaluator evaluator, SingleObjectiveThiefProblemWithFixedTour problem) {
		
		SolutionSet offsprings = new SolutionSet(populationSize);

		RandomSelection selector = new RandomSelection(population, rand);
		
		while (offsprings.size() < populationSize / 2) {

			PackingList<?> l1 = (PackingList<?>) selector.next().getVariable();
			PackingList<?> l2 = (PackingList<?>) selector.next().getVariable();

			List<IVariable> vars = new LocalOptimaCrossover().crossover(l1, l2, problem, rand);

			for (IVariable v : vars) {
				if (rand.nextDouble() < probMutation)
					v = new BitFlipMutation().mutate(v, problem, rand);
				
				offsprings.add(evaluator.evaluate(problem, (PackingList<?>) v));
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

	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();

		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				// .read("../ttp-benchmark/SingleObjective/10/10_5_6_25.txt");
				//.read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
				// .read("../ttp-benchmark/SingleObjective/10/10_15_10_75.txt");
				// .read("../ttp-benchmark/SingleObjective/20/20_5_6_75.txt");
				 .read("../ttp-benchmark/SingleObjective/20/20_20_7_50.txt");
				//.read("../ttp-benchmark/SingleObjective/20/20_30_9_25.txt");
		// .read("../ttp-benchmark/SingleObjective/50/50_15_8_50.txt");
		// .read("../ttp-benchmark/SingleObjective/100/100_5_10_50.txt");

		
		Builder<ThiefSingleObjectiveEvolutionaryAsSetAlgorithm> heur = new Builder<>(ThiefSingleObjectiveEvolutionaryAsSetAlgorithm.class);
		heur
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("name", "ThiefSingleObjectiveEvolutionaryAsSetAlgorithm");
		
		NonDominatedSolutionSet set = heur.build().run(p, new Evaluator(500000), new MyRandom(123456));

		System.out.println(p);
		System.out.println(set);
		System.out.println(Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));

	}
	
	
	
}
