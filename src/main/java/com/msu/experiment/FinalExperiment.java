package com.msu.experiment;

import java.util.List;

import com.msu.NSGAIIFactory;
import com.msu.algorithms.OnePlusOneEA;
import com.msu.io.reader.BonyadiSingleObjectiveReader;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.report.AReport;
import com.msu.moo.util.FileCollectorParser;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.IListener;
import com.msu.moo.util.events.RunFinishedEvent;
import com.msu.problems.ThiefProblem;

public class FinalExperiment extends AExperiment {


	
	private class ThiefReport extends AReport {
		public ThiefReport(String path) {
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

	protected void initialize() {
		new ThiefReport("../ttp-benchmark/thief_result_.csv");
	};
	
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();
		//fcp.add("../ttp-benchmark/SingleObjective/10", "10_*_1_*.txt", new BonyadiSingleObjectiveReader());
		//fcp.add("../ttp-benchmark/SingleObjective/20", "20_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/50", "50_*_1_*.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100", "100_*_1_*.txt", new BonyadiSingleObjectiveReader());
		//fcp.add("../ttp-benchmark/TSPLIB/berlin52-ttp", "berlin52_n51_bounded-strongly-corr_06.ttp", new BonyadiTSPLIBReader());
		List<ThiefProblem> collected = fcp.collect();
		
		/*
		collected.forEach((p) -> {
			if (p instanceof SingleObjectiveThiefProblem) 
				((SingleObjectiveThiefProblem)p).setToMultiObjective(true);
		});
		*/
		
		problems.addAll(collected);
	}
	
	
	

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[NEAREST-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-RANDOM]-[NO-HUX]-[NO-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-OPT]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[NO-HUX]-[NO-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(new OnePlusOneEA(false));
	}




	
}