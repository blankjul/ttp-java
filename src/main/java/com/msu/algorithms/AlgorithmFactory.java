package com.msu.algorithms;


import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.packing.factory.BooleanPackingListFactory;
import com.msu.thief.model.tour.factory.StandardTourFactory;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;

public class AlgorithmFactory {
	
	public static NSGAII<TTPVariable, TravellingThiefProblem> createNSGAII() {
		return createNSGAIIBuilder().create();
	}
	
	public static NSGAIIBuilder<TTPVariable, TravellingThiefProblem>  createNSGAIIBuilder() {
		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory<>(), new BooleanPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setCrossover(new TTPCrossover(new CycleCrossover<>(), new SinglePointCrossover<>()));
		builder.setProbMutation(0.3);
		return builder;
	}

}
