package com.msu.thief.experiment;

import java.util.List;

import com.msu.builder.NSGAIIBuilder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.thief.algorithm.factory.NSGAIIFactory;
import com.msu.thief.algorithms.OnePlusOneEA;
import com.msu.thief.algorithms.util.TwoOptLocalSearch;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.visualize.js.JavaScriptThiefVisualizer;
import com.msu.util.FileCollectorParser;

public class ThiefVisualizeExperiment extends AExperiment {

	@Override
	protected void setProblems(List<IProblem> problems) {

		FileCollectorParser<ThiefProblem> fcp = new FileCollectorParser<>();
		// fcp.add("../ttp-benchmark/TSPLIB/berlin52-ttp",
		// "berlin52_n51_bounded-strongly-corr_06.ttp", new
		// BonyadiTSPLIBReader());
		// fcp.add("../ttp-benchmark/SingleObjective/10", "10_*_1_*.txt", new
		// BonyadiSingleObjectiveReader());
		// fcp.add("../ttp-benchmark/SingleObjective/20", "20_*_1_*.txt", new
		// BonyadiSingleObjectiveReader());
		// fcp.add("../ttp-benchmark/SingleObjective/50", "50_*_1_*.txt", new
		// BonyadiSingleObjectiveReader());
		// fcp.add("../ttp-benchmark/SingleObjective/50", "50_*_1_*.txt", new BonyadiSingleObjectiveReader());
		// fcp.add("../ttp-benchmark/SingleObjective/20", "20_10_1_25.txt", new
		// BonyadiSingleObjectiveReader());

		// fcp.add("../ttp-benchmark/MyBenchmark", "Clustered-100*", new
		// JsonThiefProblemReader());
		// fcp.add("resources", "my_publication_coordinates.ttp", new
		// JsonThiefProblemReader());
		// fcp.add("../ttp-benchmark/MultiObjective/20", "20_*_1_*.txt", new
		// BonyadiMultiObjectiveReader());
		fcp.add("../ttp-benchmark/SingleObjective/100", "100_150_1_75.txt", new BonyadiSingleObjectiveReader());
		// fcp.add("../ttp-benchmark", "EA_example00.ttp", new
		// JsonThiefProblemReader());

		List<ThiefProblem> collected = fcp.collect();

		collected.forEach((p) -> {
			if (p instanceof SingleObjectiveThiefProblem)
				((SingleObjectiveThiefProblem) p).setToMultiObjective(true);
		});

		problems.addAll(collected);

	}

	@Override
	protected void initialize() {
		new JavaScriptThiefVisualizer("../ttp-benchmark/Bilevel");
	}

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		// algorithms.add(new ThiefExhaustive());

		//AExhaustiveAlgorithm thief = new ThiefExhaustive().setOnlyNonDominatedPoints(false);
		//thief.setName("ThiefExhaustive_ALL");
		// algorithms.add(thief);
		

		// algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		// algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[NEAREST-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-OPT]-[ERX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[NO-HUX]-[NO-BF]").build());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[ERX-HUX]-[SWAP-BF]").build());
		// algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		// algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[2OPT-RANDOM]-[CX-HUX]-[SWAP-BF]").create());
		// algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[NO-HUX]-[NO-BF]").create());
		// algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-OPT]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(new OnePlusOneEA());
		
		
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.set("funcModify", new TwoOptLocalSearch());

		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-OPT]-[CX-HUX]-[SWAP-BF]", builder).setName("CX-LOCAL").create());
		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-OPT]-[PMX-HUX]-[SWAP-BF]", builder).setName("PMX-LOCAL").create());
		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-OPT]-[OX-HUX]-[SWAP-BF]", builder).setName("OX-LOCAL").create());
		//algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[RANDOM-OPT]-[ERX-HUX]-[SWAP-BF]", builder).setName("ERX-LOCAL").create());


	}

}