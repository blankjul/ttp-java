package com.msu.thief.algorithms.impl;

import java.util.List;

import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.tour.FixedTourEvolutionOnRelevantItems;
import com.msu.thief.algorithms.impl.tour.FixedTourKnapsackWithHeuristic;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.factory.TourOptimalWithSwapFactory;
import com.msu.thief.ea.operators.TourOrderedCrossover;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefBilevelTourOnHeuristicFront extends AThiefSingleObjectiveAlgorithm {

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {
		
		IProblem<Tour> thief = new AProblem<Tour>() {

			@Override
			public int getNumberOfObjectives() {
				return 2;
			}

			@Override
			public int getNumberOfConstraints() {
				return 1;
			}

			@Override
			protected void evaluate_(Tour tour, List<Double> objectives, List<Double> constraintViolations) {
				
				tour.validate(problem.numOfCities());
				
				TourInformation tourInfo = new StandardTimeEvaluator().evaluate_(problem, tour, new Pack());
				objectives.add(tourInfo.getTime());
				
				ThiefProblemWithFixedTour tmp = new ThiefProblemWithFixedTour(problem, tour);
				Solution<Pack> opt = new FixedTourKnapsackWithHeuristic().run(tmp, evaluator, rand);
				
				objectives.add(opt.getObjective(0));
				
				constraintViolations.addAll(opt.getConstraintViolations());
			}
		};
		
		
		Builder<NSGAII<Tour, IProblem<Tour>>> b = new Builder<>(NSGAII.class);
		b
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			//.set("verbose",	true)
			.set("factory", new TourOptimalWithSwapFactory(problem))
			.set("crossover", new TourOrderedCrossover())
			.set("mutation", new TourSwapMutation());
		
		
		NSGAII<Tour, IProblem<Tour>> a = b.build();
		NonDominatedSolutionSet<Tour> set = a.run(thief, evaluator, rand);
		
		
		for (Solution<Tour> s : set) {
			ThiefProblemWithFixedTour tpwfp = new ThiefProblemWithFixedTour(problem, s.getVariable());
			Solution<Pack> opt = new FixedTourEvolutionOnRelevantItems().run(tpwfp, new Evaluator(500000), rand);
			System.out.println(opt);
		}
		System.out.println();
		
		
		return null;
	}
	

}
