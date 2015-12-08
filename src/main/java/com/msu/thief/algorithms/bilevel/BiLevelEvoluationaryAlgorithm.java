package com.msu.thief.algorithms.bilevel;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.problems.SingleObjectiveThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;
import com.msu.util.MyRandom;

public class BiLevelEvoluationaryAlgorithm extends BiLevelSingleObjectiveAlgorithms {

	
	
	@Override
	public Solution run___(SingleObjectiveThiefProblemWithFixedTour p, IEvaluator evaluator, MyRandom rand) {
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> algorithm = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		
		algorithm
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new OptimalPackingListFactory())
			.set("crossover", new HalfUniformCrossover<>())
			.set("mutation", new BitFlipMutation());
		
		Solution s = algorithm.build().run__(p, evaluator, rand);
		
		return s;
		
	}

}
