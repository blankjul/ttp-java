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
import com.msu.thief.ea.factory.ThiefOptimalTourFactory;
import com.msu.thief.ea.factory.ThiefPackOptimalFactory;
import com.msu.thief.ea.operators.ThiefBitflipMutation;
import com.msu.thief.ea.operators.ThiefOrderedCrossover;
import com.msu.thief.ea.operators.ThiefSwapMutation;
import com.msu.thief.ea.operators.ThiefUniformCrossover;
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
			.set("factory", new ThiefFactory(new ThiefOptimalTourFactory(thief), new ThiefPackOptimalFactory(thief)))
			.set("crossover", new ThiefCrossover(new ThiefOrderedCrossover(), new ThiefUniformCrossover(thief)))
			.set("mutation", new ThiefMutation(new ThiefSwapMutation(), new ThiefBitflipMutation(thief)));
		
		
		NSGAII<TTPVariable, MultiObjectiveThiefProblem> a = b.build();
		NonDominatedSolutionSet<TTPVariable> set = a.run(thief, evaluator, rand);
		
		return set;
	}

	

}
