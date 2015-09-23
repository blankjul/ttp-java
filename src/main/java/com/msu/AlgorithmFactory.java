package com.msu;



import com.msu.knp.model.factory.RandomPackingListFactory;
import com.msu.moo.algorithms.impl.NSGAII;
import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.ThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.tsp.model.factory.RandomFactory;

public class AlgorithmFactory {
	
	public static NSGAII<TTPVariable, ThiefProblem> createNSGAII() {
		return createNSGAIIBuilder().create();
	}
	
	public static NSGAIIBuilder<TTPVariable, ThiefProblem>  createNSGAIIBuilder() {
		NSGAIIBuilder<TTPVariable, ThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new RandomFactory<>(), new RandomPackingListFactory()));
		builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new SinglePointCrossover<>()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		builder.setName("NSGAII-ST[OX-SWAP]-BP[SPX-BFM]");
		return builder;
	}

}
