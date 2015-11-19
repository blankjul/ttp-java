package com.msu.thief.experiment;

import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.AReport;
import com.msu.operators.crossover.permutation.CycleCrossover;
import com.msu.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.operators.crossover.permutation.OrderedCrossover;
import com.msu.operators.crossover.permutation.PMXCrossover;
import com.msu.operators.mutation.SwapMutation;
import com.msu.soo.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.thief.io.thief.reader.SalesmanProblemReader;
import com.msu.thief.problems.ICityProblem;
import com.msu.thief.util.TwoOptMutation;
import com.msu.thief.variable.tour.factory.RandomTourFactory;
import com.msu.util.FileCollectorParser;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.RunFinishedEvent;

public class TSPOperatorSingleObjective extends AExperiment {

	private class TSPReport extends AReport {
		public TSPReport(String path) {
			super(path);
			pw.println("problem,algorithm,result");
			EventDispatcher.getInstance().register(RunFinishedEvent.class, new IListener<RunFinishedEvent>() {
				@Override
				public void handle(RunFinishedEvent event) {

					double value = (event.getNonDominatedSolutionSet().size() == 0) ? Double.NEGATIVE_INFINITY
							: event.getNonDominatedSolutionSet().get(0).getObjectives(0);

					pw.printf("%s,%s,%s\n", event.getProblem(), event.getAlgorithm(), value);
				}
			});
		}
	}

	@Override
	protected void initialize() {
		new TSPReport("/home/julesy/workspace/ttp-benchmark/single_tsp.csv");
	}

	@Override
	protected void setProblems(List<IProblem> problems) {
		FileCollectorParser<ICityProblem> fcp = new FileCollectorParser<>();
		fcp.add("resources", "*.tsp", new SalesmanProblemReader());
		problems.addAll(fcp.collect());
	}

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		
		Builder<SingleObjectiveEvolutionaryAlgorithm> singleEA = new Builder<>(SingleObjectiveEvolutionaryAlgorithm.class);
		singleEA
			.set("populationSize", 50)
			.set("factory", new RandomTourFactory())
			.set("mutation", new SwapMutation<>())
		    .set("probMutation", 0.3);
		
		algorithms.add(singleEA.set("crossover", new EdgeRecombinationCrossover<>()).set("name", "ERX-SWAP").build());
		algorithms.add(singleEA.set("crossover", new OrderedCrossover<>()).set("name", "OX-SWAP").build());
		algorithms.add(singleEA.set("crossover", new PMXCrossover<>()).set("name", "PMX-SWAP").build());
		algorithms.add(singleEA.set("crossover", new CycleCrossover<>()).set("name", "CX-SWAP").build());
		
		singleEA.set("mutation", new TwoOptMutation())
		        .set("probMutation", 0.02);
		
		algorithms.add(singleEA.set("crossover", new EdgeRecombinationCrossover<>()).set("name", "ERX-2OPT").build());
		algorithms.add(singleEA.set("crossover", new OrderedCrossover<>()).set("name", "OX-2OPT").build());
		algorithms.add(singleEA.set("crossover", new PMXCrossover<>()).set("name", "PMX-2OPT").build());
		algorithms.add(singleEA.set("crossover", new CycleCrossover<>()).set("name", "CX-2OPT").build());
		
	}

}