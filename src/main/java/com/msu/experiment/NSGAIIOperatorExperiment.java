package com.msu.experiment;

import com.msu.algorithms.OnePlusOneEA;
import com.msu.knp.model.factory.RandomPackingListFactory;
import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
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
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.tsp.model.factory.NearestNeighbourFactory;
import com.msu.tsp.model.factory.OptimumFactory;
import com.msu.tsp.model.factory.RandomFactory;
import com.msu.visualize.ThiefVisualizer;

public class NSGAIIOperatorExperiment extends AMultiObjectiveExperiment<ThiefProblem> {

	@Override
	protected void setAlgorithms(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {

		NSGAIIBuilder<TTPVariable, ThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new RandomFactory<>(), new RandomPackingListFactory()));
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
		builder.setFactory(new TTPVariableFactory(new OptimumFactory<>(), new RandomPackingListFactory()));
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new HalfUniformCrossover<Boolean>()))
				.setName("NSGAII-OPT-RANDOM").create());
		
		
		builder.setFactory(new TTPVariableFactory(new NearestNeighbourFactory<>(), new RandomPackingListFactory()));
		settings.addAlgorithm(builder.setCrossover(new TTPCrossover(new OrderedCrossover<Integer>(), new HalfUniformCrossover<Boolean>()))
				.setName("NSGAII-NEAREST-RANDOM").create());
		
		
		settings.addAlgorithm(new OnePlusOneEA());
	}


	@Override
	protected void setProblems(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		
		for (Integer cities : new Integer[] {10,25,50,100,500}) {
			for (Integer itemsPercity : new Integer[] {1,3,5,10,20}) {
				for(double rate : new Double[] {0.1, 0.4, 0.6, 0.9}) {
					settings.addProblem(new RandomTTPScenario(cities, itemsPercity, rate, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject());
				}
			}
		}
		
	}


	public void visualize() {
		new ThiefVisualizer<ThiefProblem>().show(settings, result);
	}



}