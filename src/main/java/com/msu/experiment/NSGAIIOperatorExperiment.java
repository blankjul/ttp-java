package com.msu.experiment;

import java.util.List;

import com.msu.algorithms.OnePlusOneEA;
import com.msu.knp.model.factory.RandomPackingListFactory;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.operators.crossover.HalfUniformCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.scenarios.knp.RandomKnapsackScenario.CORRELATION_TYPE;
import com.msu.scenarios.thief.RandomTTPScenario;
import com.msu.thief.ThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.tsp.model.factory.NearestNeighbourFactory;
import com.msu.tsp.model.factory.OptimumFactory;
import com.msu.tsp.model.factory.RandomFactory;
import com.msu.visualize.ThiefVisualizer;

public class NSGAIIOperatorExperiment extends AExperiment {

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {

		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFactory(new TTPVariableFactory(new RandomFactory(), new RandomPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		

		/*
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new PMXCrossover<Integer>(), new SinglePointCrossover<>()))
				.setName("NSGAII-ST[PMX-SWAP]-BP[SPX-BFM]").create());
		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new CycleCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[CX-SWAP]-BP[SPX-BFM]").create());

		
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[SPX-BFM]").create());
		
		
		builder.setFactory(new TTPVariableFactory(new RandomFactory<>(), new RandomPackingListFactory()));
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new OrderedCrossover<Integer>(), new HalfUniformCrossover<Boolean>()))
				.setName("NSGAII-RANDOM-RANDOM").create());
		*/
		builder.setFactory(new TTPVariableFactory(new OptimumFactory(), new RandomPackingListFactory()));
		algorithms.add(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new HalfUniformCrossover<Boolean>()))
				.setName("NSGAII-OPT-RANDOM").create());
		
		
		builder.setFactory(new TTPVariableFactory(new NearestNeighbourFactory(), new RandomPackingListFactory()));
		algorithms.add(builder.setCrossover(new TTPCrossover(new OrderedCrossover<Integer>(), new HalfUniformCrossover<Boolean>()))
				.setName("NSGAII-NEAREST-RANDOM").create());
		
		
		algorithms.add(new OnePlusOneEA());
	}


	@Override
	protected void setProblems(List<IProblem> problems) {
		for (Integer cities : new Integer[] {10,25,50}) {
			for (Integer itemsPercity : new Integer[] {1,3,5}) {
				for(double rate : new Double[] {0.1, 0.4, 0.6, 0.9}) {
					problems.add(new RandomTTPScenario(cities, itemsPercity, rate, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject());
				}
			}
		}
	}


	public void finalize() {
		new ThiefVisualizer<ThiefProblem>().show(this);
	}



}