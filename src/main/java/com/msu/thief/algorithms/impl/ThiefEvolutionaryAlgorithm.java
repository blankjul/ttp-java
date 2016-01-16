package com.msu.thief.algorithms.impl;

import com.msu.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.interfaces.IThiefAlgorithm;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.factory.ThiefOptimalTourFactory;
import com.msu.thief.ea.factory.ThiefPackOptimalFactory;
import com.msu.thief.ea.operators.ThiefBitflipMutation;
import com.msu.thief.ea.operators.ThiefOrderedCrossover;
import com.msu.thief.ea.operators.ThiefSwapMutation;
import com.msu.thief.ea.operators.ThiefUniformCrossover;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.util.MyRandom;

public class ThiefEvolutionaryAlgorithm implements IThiefAlgorithm {

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem thief, IEvaluator evaluator, MyRandom rand) {
		
		Builder<SingleObjectiveEvolutionaryAlgorithm<TTPVariable, AbstractThiefProblem>> b = 
				new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		
		b
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new ThiefFactory(new ThiefOptimalTourFactory(thief), new ThiefPackOptimalFactory(thief)))
			.set("crossover", new ThiefCrossover(new ThiefOrderedCrossover(), new ThiefUniformCrossover(thief)))
			.set("mutation", new ThiefMutation(new ThiefSwapMutation(), new ThiefBitflipMutation(thief)));
		
		SingleObjectiveEvolutionaryAlgorithm<TTPVariable, AbstractThiefProblem> a = b.build();
		Solution<TTPVariable> s = a.run(thief, evaluator, rand);
		
		return s;
		
	}

}
