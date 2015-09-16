package com.msu.experiment;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.Configuration;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.OneProblemNAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.RestrictedPolynomialMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.visualization.BoxPlot;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.model.tour.factory.PositionDecodedTourFactory;
import com.msu.thief.model.tour.factory.StandardTourFactory;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.scenarios.impl.RandomTSPScenario;

public class TSPOperatorExperiment extends OneProblemNAlgorithmExperiment<TravellingSalesmanProblem> {

	@Override
	protected List<IAlgorithm<TravellingSalesmanProblem>> getAlgorithms() {
		List<IAlgorithm<TravellingSalesmanProblem>> result = new ArrayList<>();

		NSGAIIBuilder<Tour<?>, TravellingSalesmanProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new StandardTourFactory<TravellingSalesmanProblem>());
		builder.setMutation(new SwapMutation<>());
		builder.setProbMutation(0.3);

		result.add(builder.setCrossover(new PMXCrossover<Integer>()).setName("NSGAII-[PMX-SWAP]").create());

		result.add(builder.setCrossover(new CycleCrossover<Integer>()).setName("NSGAII-[CX-SWAP]").create());

		result.add(builder.setCrossover(new OrderedCrossover<Integer>()).setName("NSGAII-[OX-SWAP]").create());

		result.add(builder.setCrossover(new EdgeRecombinationCrossover<Integer>()).setName("NSGAII-[ERC-SWAP]").create());

		result.add(builder.setCrossover(new SinglePointCrossover<Integer>()).setMutation(new RestrictedPolynomialMutation())
				.setFactory(new PositionDecodedTourFactory<>()).setName("NSGAII-PDT[SPC-RPM]").create());

		return result;
	}

	@Override
	protected TravellingSalesmanProblem getProblem() {
		return new TravellingSalesmanProblem(RandomTSPScenario.create(100));
	}

	@Override
	protected NonDominatedSolutionSet getTrueFront() {
		return null;
	}

	@Override
	public void report() {
		BoxPlot bp = new BoxPlot(problem.toString());
		for (IAlgorithm<?> algorithm : algorithms) {
			List<Double> series = new ArrayList<>();
			for (NonDominatedSolutionSet set : expResult.get(problem, algorithm)) {
				series.add(set.getSolutions().get(0).getObjectives().get(0));
			}
			bp.add(series, algorithm.toString());
		}
		bp.show();

	}

	public static void main(String[] args) {
		BasicConfigurator.configure();
		Configuration.pathToEAF = "../moo-java/vendor/aft-0.95/eaf";
		Configuration.pathToHV = "../moo-java/vendor/hv-1.3-src/hv";
		TSPOperatorExperiment exp = new TSPOperatorExperiment();
		exp.run(10000, 2, 6346456);
		exp.report();
	}

}