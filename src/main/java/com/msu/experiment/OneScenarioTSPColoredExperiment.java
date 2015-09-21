package com.msu.experiment;

import com.msu.knp.scenarios.impl.RandomKnapsackScenario.CORRELATION_TYPE;
import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.packing.factory.PackingListFactory;
import com.msu.thief.model.tour.factory.StandardTourFactory;
import com.msu.thief.model.tour.factory.StandardTourMutateOptimumFactory;
import com.msu.thief.scenarios.impl.RandomTTPScenario;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;import com.msu.visualize.TSPObjectiveVisualizer;

public class OneScenarioTSPColoredExperiment extends AMultiObjectiveExperiment<TravellingThiefProblem> {

	
	
	
	@Override
	public void visualize() {
		new TSPObjectiveVisualizer<TravellingThiefProblem>().show(settings, result);
	}

	@Override
	protected void setAlgorithms(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {

		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory<>(), new PackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);

		settings.addAlgorithm(
				builder.setCrossover(new TTPCrossover(new CycleCrossover<Integer>(), new SinglePointCrossover<Boolean>())).setName("CX").create());

		settings.addAlgorithm(
				builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new SinglePointCrossover<>())).setName("ERC").create());

		builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new SinglePointCrossover<>())).setName("ORD");
		settings.addAlgorithm(builder.create());

		builder.setFactory(new TTPVariableFactory(new StandardTourMutateOptimumFactory<>(), new PackingListFactory())).setName("ORD+FAC");
		settings.addAlgorithm(builder.create());
	}

	@Override
	protected void setProblems(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {

		settings.addProblem(new RandomTTPScenario(5, 100, 0.6, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject());

		/*
		 * for (Integer numOfCities : new Integer[] { 10, 20, 50, 100 }) { for
		 * (Integer numOfItemsPerCity : new Integer[] { 1, 2, 5 }) { for (Double
		 * maxWeightPerc : new Double[] { 0.25, 0.5, 0.75 }) { for
		 * (CORRELATION_TYPE type : CORRELATION_TYPE.values())
		 * settings.addProblem(new RandomTTPScenario(numOfCities,
		 * numOfItemsPerCity, maxWeightPerc, type).getObject()); } } }
		 */
	}

}