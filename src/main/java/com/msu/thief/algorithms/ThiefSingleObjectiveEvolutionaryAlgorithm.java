package com.msu.thief.algorithms;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.AbstractMutation;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class ThiefSingleObjectiveEvolutionaryAlgorithm extends ASingleObjectiveAlgorithm {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;

	// ! factory for creating new instances
	protected TTPVariableFactory factory;
	
	protected AbstractCrossover<?> crossover;
	
	protected AbstractMutation<?> mutation;

	
	@Override
	public Solution run__(IProblem p, IEvaluator evaluator, MyRandom rand) {

		AbstractThiefProblem problem = (AbstractThiefProblem) p;

		// initialize random population
		SolutionSet population = new SolutionSet(populationSize);

		for (int i = 0; i < populationSize; i++) {
			Solution s = evaluator.evaluate(problem,factory.next(problem, rand));
			population.add(s);
		}
		
		while (evaluator.hasNext()) {
			population = next(population, rand, evaluator, problem);
			
			for (Solution solution : population.subList(0, Math.min(5, population.size()))) {
				System.out.println(solution);
			}
			System.out.println("------------------------------");
			//System.out.println(evaluator.numOfEvaluations());
		}

		return population.get(0);

	}

	protected SolutionSet next(SolutionSet population, MyRandom rand, IEvaluator evaluator, IProblem problem) {
		
		// mating with random selection of the best 20 percent
		SolutionSet offsprings = new SolutionSet(populationSize);

		
		// selects per default always the maximal value
		BinaryTournamentSelection selector = new BinaryTournamentSelection(population, new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				return -1 * SingleObjectiveEvolutionaryAlgorithm.comp.compare(o1, o2);
			}
		}, rand);

		
		
		while (offsprings.size() < populationSize) {

			Tour<?> l1 = ((TTPVariable) selector.next().getVariable()).getTour();
			Tour<?> l2 = ((TTPVariable) selector.next().getVariable()).getTour();

			List<IVariable> vars = crossover.crossover(l1, l2, problem, rand);

			for (IVariable v : vars) {
				if (rand.nextDouble() < probMutation) v = mutation.mutate(v, problem, rand);
				
				PackingList<?> pack = ((TTPVariable) selector.next().getVariable()).getPackingList();
				
				Solution next = new OnePlusOneEAFixedTour().run__(new ThiefProblemWithFixedTour((AbstractThiefProblem) problem, (Tour<?>) v), new Evaluator(5000), rand);
				
				offsprings.add(evaluator.evaluate(problem, new TTPVariable((Tour<?>) v, (PackingList<?>)next.getVariable())));
			}
		}

		population.addAll(offsprings);

		// eliminate duplicates to ensure variety in the population
		population = new SolutionSet(new HashSet<>(population));
		// truncate the population -> survival of the fittest
		sortBySingleObjective(population);
		population = new SolutionSet(population.subList(0, Math.min(population.size(), populationSize)));

		System.out.println(evaluator.numOfEvaluations());
		
		return population;
		

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
