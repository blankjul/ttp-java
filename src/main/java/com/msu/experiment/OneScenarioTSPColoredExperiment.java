package com.msu.experiment;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.impl.scenarios.RandomKnapsackScenario.CORRELATION_TYPE;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.OneProblemNAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.model.packing.factory.BooleanPackingListFactory;
import com.msu.thief.model.tour.factory.StandardTourFactory;
import com.msu.thief.scenarios.impl.RandomTTPScenario;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.visualize.ColoredTourScatterPlot;

public class OneScenarioTSPColoredExperiment extends OneProblemNAlgorithmExperiment<TravellingThiefProblem> {

	@Override
	public void report() {
		ColoredTourScatterPlot sp = new ColoredTourScatterPlot("OneScenarioTSPColoredExperiment");
		for (IAlgorithm<?> algorithm : algorithms) {
			for (NonDominatedSolutionSet set : expResult.get(problem, algorithm)) {
				sp.setShowLabels(false);
				sp.add(set.getSolutions(), algorithm.toString());
			}
		}
		sp.show();
	}

	@Override
	protected TravellingThiefProblem getProblem() {
		TravellingThiefProblem ttp =  new RandomTTPScenario(50, 2, 0.4, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject();
		ttp.setName("TTP-50-10-0.7-STRONGLY_CORRELATED");
		
		ttp =  new RandomTTPScenario(100, 3, 0.5, CORRELATION_TYPE.STRONGLY_CORRELATED).getObject();
		ttp.setName("TTP-100-3-0.5-STRONGLY_CORRELATED");
		return ttp;
	}


	@Override
	protected List<IAlgorithm<TravellingThiefProblem>> getAlgorithms() {

		List<IAlgorithm<TravellingThiefProblem>> result = new ArrayList<>();

		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory<>(), new BooleanPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		
		builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new SinglePointCrossover<>())).setName("ORD");
		result.add(builder.create());

		result.add(builder.setCrossover(new TTPCrossover(new CycleCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("CX").create());
		

		result.add(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new SinglePointCrossover<>()))
				.setName("ERC").create());
		

		return result;
	}

}