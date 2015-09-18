package com.msu.experiment;

import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.RestrictedPolynomialMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.model.tour.factory.PositionDecodedTourFactory;
import com.msu.thief.model.tour.factory.StandardTourFactory;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.scenarios.impl.RandomTSPScenario;

public class TSPOperatorExperiment extends AMultiObjectiveExperiment<TravellingSalesmanProblem> {


	@Override
	protected void setAlgorithms(ExperimetSettings<TravellingSalesmanProblem, NonDominatedSolutionSet> settings) {
		NSGAIIBuilder<Tour<?>, TravellingSalesmanProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new StandardTourFactory<TravellingSalesmanProblem>());
		builder.setMutation(new SwapMutation<>());
		builder.setProbMutation(0.3);

		settings.addAlgorithm(builder.setCrossover(new PMXCrossover<Integer>()).setName("NSGAII-[PMX-SWAP]").create());

		settings.addAlgorithm(builder.setCrossover(new CycleCrossover<Integer>()).setName("NSGAII-[CX-SWAP]").create());

		settings.addAlgorithm(builder.setCrossover(new OrderedCrossover<Integer>()).setName("NSGAII-[OX-SWAP]").create());

		settings.addAlgorithm(builder.setCrossover(new EdgeRecombinationCrossover<Integer>()).setName("NSGAII-[ERC-SWAP]").create());

		settings.addAlgorithm(builder.setCrossover(new SinglePointCrossover<Integer>()).setMutation(new RestrictedPolynomialMutation())
				.setFactory(new PositionDecodedTourFactory<>()).setName("NSGAII-PDT[SPC-RPM]").create());
		
	}

	@Override
	protected void setProblems(ExperimetSettings<TravellingSalesmanProblem, NonDominatedSolutionSet> settings) {
		settings.addProblem(new TravellingSalesmanProblem(RandomTSPScenario.create(100)));
	}

}