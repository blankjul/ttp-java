package com.msu.thief.experiment;

import java.util.List;
import java.util.function.Function;

import com.msu.builder.NSGAIIBuilder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.AReport;
import com.msu.thief.NSGAIIFactory;
import com.msu.thief.io.thief.reader.SalesmanProblemReader;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.ICityProblem;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.problems.ThiefProblem;
import com.msu.util.FileCollectorParser;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.RunFinishedEvent;

public class TSPOperator extends AExperiment {

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
		new TSPReport("/home/julesy/workspace/ttp-benchmark/single_as_multi_tsp.csv");
	}

	
	@Override
	protected void setProblems(List<IProblem> problems) {
		FileCollectorParser<ICityProblem> fcp = new FileCollectorParser<>();
		fcp.add("resources", "*.tsp", new Function<String, ThiefProblem>() {
			@Override
			public ThiefProblem apply(String t) {
				SalesmanProblem tsp = new SalesmanProblemReader().read(t);
				ThiefProblem problem = new ThiefProblem(tsp.getMap(), new ItemCollection<>(), 0);
				problem.setName(tsp.getName());
				return problem;
			}
		});
		problems.addAll(fcp.collect());
	}
	

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		NSGAIIBuilder builder = new NSGAIIBuilder();
		
		builder
		.set("populationSize", 50)
	    .set("probMutation", 0.3);
		
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[CX-NO]-[SWAP-NO]", builder).set("name","CX-SWAP").build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[PMX-NO]-[SWAP-NO]", builder).set("name","PMX-SWAP").build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-NO]-[SWAP-NO]", builder).set("name","OX-SWAP").build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[ERX-NO]-[SWAP-NO]", builder).set("name","ERX-SWAP").build());
		
		builder.set("probMutation", 0.02);
		
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[CX-NO]-[2OPT-NO]", builder).set("name","CX-2OPT").build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[PMX-NO]-[2OPT-NO]", builder).set("name","PMX-2OPT").build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-NO]-[2OPT-NO]", builder).set("name","OX-2OPT").build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[ERX-NO]-[2OPT-NO]", builder).set("name","ERX-2OPT").build());

	}

}