package com.msu.thief.experiment;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.OneProblemNAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.HalfUniformCrossover;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.factory.ThiefProblemFactory;
import com.msu.thief.factory.items.ItemFactory;
import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.model.packing.BooleanPackingListFactory;
import com.msu.thief.model.tour.StandardTourFactory;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.variable.TTPCrossover;
import com.msu.thief.variable.TTPMutation;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.TTPVariableFactory;
import com.msu.thief.visualize.ColoredTourScatterPlot;

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
		return new ThiefProblemFactory(new MapFactory(), new ItemFactory(ItemFactory.CORRELATION_TYPE.UNCORRELATED), 0.5, "TTP-20-10-STRONGLY")
				.create(50, 10);
	}

	@Override
	protected NonDominatedSolutionSet getTrueFront() {
		return null;
	}

	@Override
	protected List<IAlgorithm<TravellingThiefProblem>> getAlgorithms() {

		List<IAlgorithm<TravellingThiefProblem>> result = new ArrayList<>();

		NSGAIIBuilder<TTPVariable, TravellingThiefProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new TTPVariableFactory(new StandardTourFactory<>(), new BooleanPackingListFactory()));
		builder.setMutation(new TTPMutation(new SwapMutation<>(), new BitFlipMutation()));
		builder.setProbMutation(0.3);
		builder.setCrossover(new TTPCrossover(new OrderedCrossover<>(), new SinglePointCrossover<>())).setName("NSGAII-ST[ORD-SWAP]-BP[SPX-BFM]");
		result.add(builder.create());

		result.add(builder.setCrossover(new TTPCrossover(new CycleCrossover<Integer>(), new SinglePointCrossover<Boolean>()))
				.setName("NSGAII-ST[CX-SWAP]-BP[SPX-BFM]").create());
		

		result.add(builder.setCrossover(new TTPCrossover(new EdgeRecombinationCrossover<Integer>(), new HalfUniformCrossover<>()))
				.setName("NSGAII-ST[ERX-SWAP]-BP[HUX-BFM]").create());
		

		return result;
	}

}