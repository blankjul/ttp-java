package com.msu.thief.algorithms.impl.moo;

import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.ILocalOptimization;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.bilevel.pack.FixedPackSwapTour;
import com.msu.thief.algorithms.interfaces.AThiefMultiObjectiveAlgorithm;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.factory.PackOptimalFactory;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.ea.operators.TourOrderedCrossover;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedPack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefMultiObjectiveAlgorithm extends AThiefMultiObjectiveAlgorithm {

	@Override
	public NonDominatedSolutionSet<TTPVariable> run_(MultiObjectiveThiefProblem thief, IEvaluator evaluator,
			MyRandom rand) {

		// local optimization of the tour of every individual
		class TourLocalOptimization implements ILocalOptimization<TTPVariable, AbstractThiefProblem> {
			@Override
			public Solution<TTPVariable> run(AbstractThiefProblem problem, IEvaluator l, TTPVariable var) {
				
				/*				
				ThiefProblemWithFixedPack packProblem = new ThiefProblemWithFixedPack(thief, var.getPack());
				Solution<Tour> local =  FixedPackSwapTour.localSwaps(packProblem, l, packProblem.evaluate(var.getTour()), 20);
				
				return problem.evaluate(TTPVariable.create(local.getVariable(), var.getPack()));
				*/
				
				if (rand.nextDouble() > 1 / 50) return l.evaluate(problem, var);
				
				Solution<Tour> localTour = new FixedPackSwapTour(var.getTour())
						.run(new ThiefProblemWithFixedPack(thief, var.getPack()), l, rand);
				
				Solution<TTPVariable> opt = problem.evaluate(TTPVariable.create(localTour.getVariable(), var.getPack()));
				
				return opt;
			}
		}

		Builder<NSGAII<TTPVariable, MultiObjectiveThiefProblem>> b = new Builder<>(NSGAII.class);
		b.set("populationSize", 50)
				.set("probMutation", 0.3)
				//.set("local", new TourLocalOptimization())
				// .set("verbose", true)
				.set("factory", new ThiefFactory(new TourOptimalFactory(thief), new PackOptimalFactory(thief)))
				.set("crossover", new ThiefCrossover(new TourOrderedCrossover(), new PackUniformCrossover(thief)))
				.set("mutation", new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(thief)));

		NSGAII<TTPVariable, MultiObjectiveThiefProblem> a = b.build();
		NonDominatedSolutionSet<TTPVariable> set = a.run(thief, evaluator, rand);

		return set;
	}

}
