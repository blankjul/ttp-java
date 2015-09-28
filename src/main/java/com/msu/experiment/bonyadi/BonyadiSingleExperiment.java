package com.msu.experiment.bonyadi;

import java.util.List;

import com.msu.algorithms.OnePlusOneEA;
import com.msu.algorithms.RandomLocalSearch;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.report.SingleObjectiveReport;
import com.msu.scenarios.thief.bonyadi.BenchmarkSingleObjective;
import com.msu.util.Util;

public class BonyadiSingleExperiment extends ABonyadiBenchmark {

	final public static String FOLDER = "../ttp-benchmark/SingleObjective/10";

	@Override
	protected void finalize() {
		new SingleObjectiveReport().print(this);
	}
	

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		super.setAlgorithms(algorithms);
		algorithms.add(new OnePlusOneEA());
		algorithms.add(new RandomLocalSearch());
	}


	@Override
	protected void setProblems(List<IProblem> problems) {
		for (String path : Util.getFiles(FOLDER)) {
			problems.add(new BenchmarkSingleObjective().create(path));
		}
	}

}
