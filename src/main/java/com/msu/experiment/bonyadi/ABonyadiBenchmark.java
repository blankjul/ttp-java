package com.msu.experiment.bonyadi;

import java.util.List;

import com.msu.knp.model.factory.EmptyPackingListFactory;
import com.msu.knp.model.factory.FullPackingListFactory;
import com.msu.knp.model.factory.RandomPackingListFactory;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.operators.crossover.HalfUniformCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.tsp.model.factory.NearestNeighbourFactory;
import com.msu.tsp.model.factory.OptimumFactory;
import com.msu.tsp.model.factory.RandomFactory;

public abstract class ABonyadiBenchmark extends AExperiment {



	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new HalfUniformCrossover<>()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.4);
		builder.setPopulationSize(50);
		
		builder.setFactory(new TTPVariableFactory(new OptimumFactory(), new FullPackingListFactory()));
		algorithms.add(builder.setName("NSGAII-[OPT-FULL]-[OX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new OptimumFactory(), new EmptyPackingListFactory()));
		algorithms.add(builder.setName("NSGAII-[OPT-EMPTY]-[OX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new OptimumFactory(), new RandomPackingListFactory()));
		algorithms.add(builder.setName("NSGAII-[OPT-RANDOM]-[OX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new RandomFactory(), new FullPackingListFactory()));
		algorithms.add(builder.setName("NSGAII-[RANDOM-FULL]-[OX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new RandomFactory(), new EmptyPackingListFactory()));
		algorithms.add(builder.setName("NSGAII-[RANDOM-EMPTY]-[OX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new RandomFactory(), new RandomPackingListFactory()));
		algorithms.add(builder.setName("NSGAII-[RANDOM-RANDOM]-[OX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new NearestNeighbourFactory(), new FullPackingListFactory()));
		algorithms.add(builder.setName("NSGAII-[NEAREST-FULL]-[OX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new NearestNeighbourFactory(), new EmptyPackingListFactory()));
		algorithms.add(builder.setName("NSGAII-[NEAREST-EMPTY]-[OX-SWAP]-[HUX-BFM]").create());
		
		builder.setFactory(new TTPVariableFactory(new NearestNeighbourFactory(), new RandomPackingListFactory()));
		algorithms.add(builder.setName("NSGAII-[NEAREST-RANDOM]-[OX-SWAP]-[HUX-BFM]").create());
		
		//algorithms.add(new OnePlusOneEA());
		//algorithms.add(new RandomLocalSearch());
	}


}
