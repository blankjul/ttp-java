package com.msu;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.io.reader.JsonThiefReader;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.scenarios.thief.bonyadi.BenchmarkTSPLIB;
import com.msu.visualize.js.JavaScriptThiefVisualizer;

public class ThiefVisualizeExperiment extends AExperiment {

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[NEAREST-EMPTY]-[OX-HUX]-[SWAP-BF]").create());
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-EMPTY]-[OX-HUX]-[SWAP-BF]").create());

	}

	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.add(new JsonThiefReader().read("../ttp-benchmark/my_publication_coordinates.ttp"));
		//problems.add(new BenchmarkTSPLIB().read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_uncorr-similar-weights_01.ttp"));
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();
		AExperiment experiment = new ThiefVisualizeExperiment();
		experiment.run(1000, 1, 123456);
		new JavaScriptThiefVisualizer().write(experiment, "../ttp-benchmark/berlin52_n51_uncorr-similar-weights_01.html");
	}

	
}