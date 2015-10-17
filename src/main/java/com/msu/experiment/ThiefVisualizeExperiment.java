package com.msu.experiment;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.NSGAIIFactory;
import com.msu.algorithms.OnePlusOneEA;
import com.msu.algorithms.exhaustive.AExhaustiveAlgorithm;
import com.msu.algorithms.exhaustive.ThiefExhaustive;
import com.msu.io.reader.BonyadiTSPLIBReader;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.FileCollectorParser;
import com.msu.problems.SingleObjectiveThiefProblem;
import com.msu.problems.ThiefProblem;
import com.msu.visualize.js.JavaScriptThiefVisualizer;

public class ThiefVisualizeExperiment extends AExperiment {

	
	public static final int EVALUATIONS = 1000000;
	
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark/TSPLIB/berlin52-ttp", "berlin52_n51_bounded-strongly-corr_06.ttp", new BonyadiTSPLIBReader());
		//fcp.add("../ttp-benchmark/SingleObjective/10", "10_*_1_*.txt", new BonyadiSingleObjectiveReader());
		//fcp.add("../ttp-benchmark/SingleObjective/20", "20_*_1_*.txt", new BonyadiSingleObjectiveReader());
		//fcp.add("../ttp-benchmark/SingleObjective/50", "50_*_1_*.txt", new BonyadiSingleObjectiveReader());
		//fcp.add("../ttp-benchmark/SingleObjective/50", "50_75_1_50.txt", new BonyadiSingleObjectiveReader());
		//fcp.add("../ttp-benchmark/MyBenchmark", "Clustered-100*", new JsonThiefProblemReader());
		//fcp.add("resources", "my_publication_coordinates.ttp", new JsonThiefProblemReader());
		//fcp.add("../ttp-benchmark/MultiObjective/20", "20_*_1_*.txt", new BonyadiMultiObjectiveReader());
		
		//fcp.add("../ttp-benchmark", "EA_example00.ttp", new JsonThiefProblemReader());
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
		//algorithms.add(new ThiefExhaustive());
		
		AExhaustiveAlgorithm thief = new ThiefExhaustive().setOnlyNonDominatedPoints(false);
		thief.setName("ThiefExhaustive_ALL");
		//algorithms.add(thief);
		
		
		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[NEAREST-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-RANDOM]-[NO-HUX]-[NO-BF]").create());
		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-OPT]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[NO-HUX]-[NO-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(new OnePlusOneEA());
		//algorithms.add(new OnePlusOneEA(false));
	}



	public static void main(String[] args) {
		BasicConfigurator.configure();
		AExperiment experiment = new ThiefVisualizeExperiment();
		experiment.run(EVALUATIONS, 1, 123456);
	}

	
}