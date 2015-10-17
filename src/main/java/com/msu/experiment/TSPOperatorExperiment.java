package com.msu.experiment;

import java.util.List;
import java.util.function.Function;

import com.msu.NSGAIIFactory;
import com.msu.io.reader.SalesmanProblemReader;
import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.report.AReport;
import com.msu.moo.util.FileCollectorParser;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.IListener;
import com.msu.moo.util.events.RunFinishedEvent;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.model.ItemCollection;

public class TSPOperatorExperiment extends AExperiment {

	
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
		new TSPReport("/home/julesy/publications/ttp-publication-nsgaII/Experiments/proof_of_principle_tsp.csv");
	}


	@Override
	protected void setProblems(List<IProblem> problems) {
			
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();

		fcp.add("resources", "*.tsp", new Function<String, ThiefProblem>() {

			@Override
			public ThiefProblem apply(String t) {
				SalesmanProblem tsp = new SalesmanProblemReader().read(t);
				ThiefProblem problem = new ThiefProblem(tsp.getMap(), new ItemCollection<>(), 10);
				problem.setName(tsp.getName());
				return problem;
			}

		});
		problems.addAll(fcp.collect());

	}
	

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setPopulationSize(500);
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[PMX-NO]-[SWAP-NO]", builder).setName("PMX").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[CX-NO]-[SWAP-NO]", builder).setName("CX").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-NO]-[SWAP-NO]", builder).setName("OX").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[ERX-NO]-[SWAP-NO]", builder).setName("ERX").create());
	}

}