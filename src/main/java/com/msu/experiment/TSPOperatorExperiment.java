package com.msu.experiment;

import java.util.List;

import com.msu.io.reader.SalesmanProblemReader;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.operators.crossover.permutation.CycleCrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.operators.crossover.permutation.OrderedCrossover;
import com.msu.moo.operators.crossover.permutation.PMXCrossover;
import com.msu.moo.operators.mutation.SwapMutation;
import com.msu.moo.util.FileCollectorParser;
import com.msu.moo.util.io.AReader;
import com.msu.problems.SalesmanProblem;
import com.msu.thief.variable.tour.factory.NearestNeighbourFactory;

public class TSPOperatorExperiment extends AExperiment {

	
	@Override
	protected void setProblems(List<IProblem> problems) {
		AReader<SalesmanProblem> r = new SalesmanProblemReader(); 
		FileCollectorParser<SalesmanProblem> fcp = new FileCollectorParser<>();
		fcp.add("resources", "bays29.tsp", r);
		fcp.add("resources", "berlin52.tsp", r);
		fcp.add("resources", "eil101.tsp", r);
		fcp.add("resources", "d198.tsp", r);
		problems.addAll(fcp.collect());
	}
	

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFactory(new NearestNeighbourFactory());
		builder.setMutation(new SwapMutation<>());
		builder.setProbMutation(0.3);
		builder.setPopulationSize(1000);
		algorithms.add(builder.setCrossover(new PMXCrossover<Integer>()).setName("PMX").create());
		algorithms.add(builder.setCrossover(new CycleCrossover<Integer>()).setName("CX").create());
		algorithms.add(builder.setCrossover(new OrderedCrossover<Integer>()).setName("OX").create());
		algorithms.add(builder.setCrossover(new EdgeRecombinationCrossover<Integer>()).setName("ERC").create());
	}

}