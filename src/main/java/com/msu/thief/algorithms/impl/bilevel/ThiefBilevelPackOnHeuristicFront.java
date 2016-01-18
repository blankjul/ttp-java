package com.msu.thief.algorithms.impl.bilevel;

import java.util.List;

import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.factory.PackOptimalFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.evaluator.PackingInformation;
import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefBilevelPackOnHeuristicFront extends AThiefSingleObjectiveAlgorithm {

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {
		
		Tour best = AlgorithmUtil.calcBestTour(problem);
		
		IProblem<Pack> thief = new AProblem<Pack>() {

			@Override
			public int getNumberOfObjectives() {
				return 2;
			}

			@Override
			public int getNumberOfConstraints() {
				return 1;
			}

			@Override
			protected void evaluate_(Pack var, List<Double> objectives, List<Double> constraintViolations) {
				
				Tour t = best;
				Pack p = var;
				
				t.validate(problem.numOfCities());
				p.validate(problem.numOfItems());
				
				TourInformation tourInfo = new StandardTimeEvaluator().evaluate_(problem, t, p);
				objectives.add(tourInfo.getTime());
				
				PackingInformation packInfo = new NoDroppingEvaluator().evaluate_(problem, t, p, tourInfo);
				objectives.add(- packInfo.getProfit());

				final double weight = packInfo.getWeight();
				constraintViolations.add(Math.max(0, weight - problem.getMaxWeight()));
			}
		};
		
		
		Builder<NSGAII<Pack, IProblem<Pack>>> b = new Builder<>(NSGAII.class);
		b
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			//.set("verbose",	true)
			.set("factory", new PackOptimalFactory(problem))
			.set("crossover", new PackUniformCrossover(problem))
			.set("mutation", new PackBitflipMutation(problem));
		
		NSGAII<Pack, IProblem<Pack>> a = b.build();
		NonDominatedSolutionSet<Pack> set = a.run(thief, evaluator, rand);
		
		
		for (Solution<Pack> s : set) {
			
			System.out.println(s);
		}
		System.out.println();
		
		
		return null;
	}
	

}
