package com.msu.experiment;

import java.util.List;

import com.google.common.collect.Multimap;
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
import com.msu.moo.report.SingleObjectiveReport;
import com.msu.moo.util.events.FinishedProblemExecution;
import com.msu.moo.util.events.IEvent;
import com.msu.moo.util.events.IListener;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.model.factory.NearestNeighbourFactory;

public class TSPOperatorExperiment extends AExperiment {

	protected final String[] SCENARIOS = new String[] { 
			"resources/bays29.tsp",
			"resources/berlin52.tsp",
			"resources/eil101.tsp",
			"resources/d198.tsp",
	};
	

	@Override
	protected void setListener(Multimap<Class<?>, IListener<? extends IEvent>> listener) {
		SingleObjectiveReport report = new SingleObjectiveReport();
		report.set("experiment/TSPOperator_result.csv");
		listener.put(FinishedProblemExecution.class, report);
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

	@Override
	protected void setProblems(List<IProblem> problems) {
		
		for (String scenario : SCENARIOS) {
			TravellingSalesmanProblem tsp = new SalesmanProblemReader().read(scenario);
			problems.add(tsp);
			
			/*
			ThiefProblem problem = new ThiefProblem(new SymmetricMap(1), new ItemCollection<>(), 0);
			problem.setName(tsp.getName());
			problems.add(problem);
			*/
		}
		
	}
	

}