package com.msu.experiment;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.NSGAIIFactory;
import com.msu.io.reader.JsonThiefProblemReader;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.FileCollectorParser;
import com.msu.problems.SingleObjectiveThiefProblem;
import com.msu.problems.ThiefProblem;
import com.msu.visualize.js.JavaScriptThiefVisualizer;

public class ThiefVisualizeExperiment extends AExperiment {

	
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();
		//fcp.add("../ttp-benchmark/TSPLIB/berlin52-ttp", "berlin52_n51_uncorr_01.ttp", new BonyadiTSPLIBReader());
		//fcp.add("../ttp-benchmark/SingleObjective/10", "10_*_1_25.txt", new BonyadiSingleObjectiveReader());
		//fcp.add("../ttp-benchmark/SingleObjective/100", "100_*_1_25.txt", new BonyadiSingleObjectiveReader());
		fcp.add("../ttp-benchmark", "opt_tour_performs_optimal_20_cities_clustered.ttp", new JsonThiefProblemReader());
		List<ThiefProblem> collected = fcp.collect();
		
		collected.forEach((p) -> {
			if (p instanceof SingleObjectiveThiefProblem) 
				((SingleObjectiveThiefProblem)p).setToMultiObjective(true);
		});
		
		problems.addAll(collected);
	}
	
	
	
	@Override
	protected void initialize() {
		new JavaScriptThiefVisualizer("../ttp-benchmark");
	}
	

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		//algorithms.add(new ExhaustiveThief());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[NEAREST-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[NO-HUX]-[NO-BF]").create());
		//algorithms.add(new OnePlusOneEA());
		//algorithms.add(new OnePlusOneEA(false));
	}



	public static void main(String[] args) {
		BasicConfigurator.configure();
		AExperiment experiment = new ThiefVisualizeExperiment();
		experiment.run(100000, 1, 123456);
	}

	
}