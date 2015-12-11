package com.msu.thief.algorithms.bilevel;

import com.msu.builder.Builder;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.algorithms.BiLevelAlgorithms;
import com.msu.thief.variable.pack.factory.OptimalPackingListFactory;

public class BiLevelEvoluationaryAlgorithm extends BiLevelAlgorithms {

	
	
	public BiLevelEvoluationaryAlgorithm() {
		this(0);
	}

	public BiLevelEvoluationaryAlgorithm(int numOf2OptTours) {

		Builder<SingleObjectiveEvolutionaryAlgorithm> algorithm = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		algorithm
			.set("populationSize", 50)
			.set("probMutation", 0.3)
			.set("factory", new OptimalPackingListFactory())
			.set("crossover", new UniformCrossover<>())
			.set("mutation", new BitFlipMutation());
		
		this.algorithm = algorithm.build();
		this.numOf2OptTours = numOf2OptTours;
	}


}
