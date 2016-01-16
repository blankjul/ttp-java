package com.msu.thief.algorithms.impl;

import java.util.List;

import com.msu.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.model.AProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.algorithms.impl.tour.FixedTourKnapsackWithHeuristic;
import com.msu.thief.algorithms.interfaces.IThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.factory.ThiefPackOptimalFactory;
import com.msu.thief.ea.operators.ThiefBitflipMutation;
import com.msu.thief.ea.operators.ThiefUniformCrossover;
import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;

public class ThiefBilevelTourOnHeuristicFront implements IThiefSingleObjectiveAlgorithm {

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {
		
		Tour best = AlgorithmUtil.calcBestTour(problem);
		
		
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
			protected void evaluate_(Tour var, List<Double> objectives, List<Double> constraintViolations) {
				
				Tour tour = best;
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
			.set("factory", new ThiefPackOptimalFactory(problem))
			.set("crossover", new ThiefUniformCrossover(problem))
			.set("mutation", new ThiefBitflipMutation(problem));
		
		NSGAII<Tour, IProblem<Tour>> a = b.build();
		NonDominatedSolutionSet<Tour> set = a.run(thief, evaluator, rand);
		
		
		for (Solution<Tour> s : set) {
			System.out.println(s);
		}
		System.out.println();
		
		
		return null;
	}
	

}
