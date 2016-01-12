package com.msu.thief.algorithms;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.thief.algorithms.bilevel.tour.SolveKnapsackWithHeuristicValues;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.algorithms.recombinations.InverseSwapMutation;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class BestSoFarAlgorithm extends ASingleObjectiveAlgorithm {

	@Override
	public Solution run__(IProblem problem, IEvaluator evaluator, MyRandom rand) {

		SingleObjectiveThiefProblem thief = (SingleObjectiveThiefProblem) problem;

		Tour<?> bestTour = AlgorithmUtil.calcBestTour(thief);
		Solution best = new SolveKnapsackWithHeuristicValues()
				.run__(new ThiefProblemWithFixedTour(thief, bestTour), new Evaluator(50000), rand);
		
		boolean improvement = true;
		
		while (improvement) {

			improvement = false;

			outer: 
			for (int i = thief.numOfCities() - 2; i > 0; i--) {

				for (int k = i - 1; k > 0; k--) {

					// create the new tour
					
					Tour<?> nextTour = new StandardTour(
							new InverseSwapMutation().mutate_(bestTour.encode(), thief, rand, null, k, i));

					Solution next = new SolveKnapsackWithHeuristicValues()
							.run__(new ThiefProblemWithFixedTour(thief, nextTour), evaluator, rand);

					if (new SolutionDominatorWithConstraints().isDominating(next, best)) {
						best = next;
						bestTour = nextTour;
						//System.out.print("-> ");
						//System.out.println(String.format("%s %s swap %s", i, k, next));
						improvement = true;
						break outer;
					}

				}
			}
		}

		//Solution next = new OnePlusOneEAFixedTour().run___(new ThiefProblemWithFixedTour(thief, bestTour),
				//new Evaluator(50000), rand);
		
		return thief.evaluate(new TTPVariable(bestTour, (PackingList<?>) best.getVariable()));
		
				
		/*

		IEvaluator first = evaluator.createChildEvaluator(evaluator.getMaxEvaluations() / 2);

		Builder<SingleObjectiveEvolutionaryAlgorithm> builder = new Builder<>(
				SingleObjectiveEvolutionaryAlgorithm.class);
		builder.set("populationSize", 50).set("probMutation", 0.3).set("crossover", new OrderedCrossover<>())
				.set("mutation", new SwapMutation<>()).set("factory", new OptimalTourFactory())
				.set("name", "NSGAII-TOUR-HEUR");

		SingleObjectiveEvolutionaryAlgorithm soea = builder.build();

		Solution s = soea.run__(new TourHeuristicProblem(thief, first), first, rand);

		Tour<?> tour = (Tour<?>) s.getVariable();
		Solution local = new SolveKnapsackWithHeuristicValues().run___(new ThiefProblemWithFixedTour(thief, tour),
				evaluator, rand);
		PackingList<?> pack = new BooleanPackingList(((PackingList<?>) local.getVariable()).encode());

		Solution result = new OnePlusOneEAFixedTour(pack).run___(new ThiefProblemWithFixedTour(thief, tour), evaluator,
				rand);

		return thief.evaluate(new TTPVariable(tour, (PackingList<?>) result.getVariable()));
	}*/

	}
}
