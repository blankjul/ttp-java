package com.msu.thief.algorithms.impl;

import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.ThiefCrossover;
import com.msu.thief.ea.ThiefFactory;
import com.msu.thief.ea.ThiefMutation;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.ea.factory.PackOptimalFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.ea.operators.PackUniformCrossover;
import com.msu.thief.ea.operators.TourOrderedCrossover;
import com.msu.thief.ea.operators.TourOrderedMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class ThiefEvolutionaryAlgorithm extends AThiefSingleObjectiveAlgorithm {

	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem thief, IEvaluator evaluator, MyRandom rand) {
		
		
		
		Builder<SingleObjectiveEvolutionaryAlgorithm<TTPVariable, AbstractThiefProblem>> b = 
				new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		
		b
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("verbose",	false)
			.set("factory", new ThiefFactory(new TourOptimalFactory(thief), new PackOptimalFactory(thief)))
			.set("crossover", new ThiefCrossover(new TourOrderedCrossover(), new PackUniformCrossover(thief)))
			.set("mutation", new ThiefMutation(new TourOrderedMutation(), new PackBitflipMutation(thief)));
		
		SingleObjectiveEvolutionaryAlgorithm<TTPVariable, AbstractThiefProblem> a = b.build();
		Solution<TTPVariable> s = a.run(thief, evaluator, rand);
		
		return s;
		
	}

}
