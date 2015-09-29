package com.msu.experiment.bonyadi;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.msu.algorithms.OnePlusOneEA;
import com.msu.algorithms.RandomLocalSearch;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.report.SingleObjectiveReport;
import com.msu.moo.util.Util;
import com.msu.moo.visualization.ObjectiveBoxPlot;
import com.msu.scenarios.thief.bonyadi.BenchmarkSingleObjective;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.util.ThiefUtil;

public class BonyadiSingleExperiment extends ABonyadiBenchmark {

	final public static String FOLDER = "../ttp-benchmark/SingleObjective/10/10_3_1_25.txt";

	@Override
	protected void finalize() {
		new ObjectiveBoxPlot().show(this);
		
		StringBuffer sb =  new SingleObjectiveReport().print(this);
		if (hasOutputDirectory()) Util.write(String.format("%s/%s", getOutputDir(), "SO_result.csv"),sb);
		else System.out.println(sb);
		
	}
	

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		super.setAlgorithms(algorithms);
		algorithms.add(new OnePlusOneEA());
		algorithms.add(new RandomLocalSearch());
	}


	@Override
	protected void setProblems(List<IProblem> problems) {
		List<String> l = ThiefUtil.getFiles(FOLDER);
		Collections.sort(l);
		for (String path : l) {
			if (!path.endsWith("75.txt"))  continue;
			SingleObjectiveThiefProblem problem = new BenchmarkSingleObjective().create(path);
			problem.setName("SO_" + new File(path).getName().split("\\.")[0]);
			problems.add(problem);
		}
	}

}
