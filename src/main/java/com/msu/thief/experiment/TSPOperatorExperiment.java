package com.msu.thief.experiment;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.experiment.SingeĺeObjectiveExperiment;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.RestrictedPolynomialMutation;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.model.tour.PositionDecodedTourFactory;
import com.msu.thief.model.tour.StandardTourFactory;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.tsp.TravellingSalesmanProblem;

public class TSPOperatorExperiment extends SingeĺeObjectiveExperiment<TravellingSalesmanProblem> {


	@Override
	protected void setAlgorithms() {

		NSGAIIBuilder<Tour<?>, TravellingSalesmanProblem> builder = new NSGAIIBuilder<>();
		builder.setFactory(new StandardTourFactory<TravellingSalesmanProblem>());
		builder.setMutation(new SwapMutation<>());
		builder.setProbMutation(0.3);

		algorithms.add(builder.setCrossover(new PMXCrossover<Integer>())
				.setName("NSGAII-[PMX-SWAP]").create());
		
		algorithms.add(builder.setCrossover(new CycleCrossover<Integer>())
				.setName("NSGAII-[CX-SWAP]").create());
		
		algorithms.add(builder.setCrossover(new OrderedCrossover<Integer>())
				.setName("NSGAII-[OX-SWAP]").create());
		
		algorithms.add(builder.setCrossover(new EdgeRecombinationCrossover<Integer>())
				.setName("NSGAII-[ERC-SWAP]").create());
		
		RandomSearch<Tour<?>, TravellingSalesmanProblem> rs = new RandomSearch<>(new PositionDecodedTourFactory<>());
		rs.setName("Random-PDT[SPC-RPM]");
		algorithms.add(rs);
		
		algorithms.add(builder.setCrossover(new SinglePointCrossover<Integer>())
				.setMutation(new RestrictedPolynomialMutation())
				.setFactory(new PositionDecodedTourFactory<>())
				.setName("NSGAII-PDT[SPC-RPM]").create());

	}

	@Override
	protected void setProblem() {
		problem = new TravellingSalesmanProblem(new MapFactory().create(30));
	}
	
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		TSPOperatorExperiment exp = new TSPOperatorExperiment();
		exp.setPathToEAF("../moo-java/vendor/aft-0.95/eaf");
		exp.setPathToHV("../moo-java/vendor/hv-1.3-src/hv");
		exp.run(50000, 10, 12337657);
		exp.visualize();
	}
	
	
}