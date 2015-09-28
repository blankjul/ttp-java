package com.msu.experiment.bonyadi;

import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.scenarios.thief.bonyadi.BenchmarkTSPLIB;
import com.msu.util.Util;

public class BonyadiTSPLIBExperiment extends ABonyadiBenchmark {

	final public static String FOLDER = "../ttp-benchmark/TSPLIB/eil51-ttp/eil51_n50_uncorr-similar-weights_05.ttp";

	@Override
	protected void setProblems(List<IProblem> problems) {
		for (String path : Util.getFiles(FOLDER)) {
			problems.add(new BenchmarkTSPLIB().create(path));
		}
	}

	

}
