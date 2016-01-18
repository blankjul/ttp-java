package com.msu.thief.algorithms.impl.moo;

import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.interfaces.AThiefMultiObjectiveAlgorithm;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.ea.factory.PackOptimalFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.TourOrderedCrossover;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class ThiefMultiObjectiveAlgorithm extends AThiefMultiObjectiveAlgorithm {

	@Override
	public NonDominatedSolutionSet<TTPVariable> run_(MultiObjectiveThiefProblem thief, IEvaluator evaluator,
			MyRandom rand) {

		Builder<NSGAII<TTPVariable, MultiObjectiveThiefProblem>> b = new Builder<>(NSGAII.class);
		b
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			//.set("verbose",	true)
			.set("factory", new ThiefFactory(new TourOptimalFactory(thief), new PackOptimalFactory(thief)))
			.set("crossover", new ThiefCrossover(new TourOrderedCrossover(), new PackUniformCrossover(thief)))
			.set("mutation", new ThiefMutation(new TourSwapMutation(), new PackBitflipMutation(thief)));
		
		
		NSGAII<TTPVariable, MultiObjectiveThiefProblem> a = b.build();
		NonDominatedSolutionSet<TTPVariable> set = a.run(thief, evaluator, rand);
		
		return set;
	}

	

}
