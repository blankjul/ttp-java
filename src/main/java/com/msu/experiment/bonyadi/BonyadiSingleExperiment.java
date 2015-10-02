package com.msu.experiment.bonyadi;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import com.msu.algorithms.OnePlusOneEA;
import com.msu.algorithms.RandomLocalSearch;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.visualization.ObjectiveBoxPlot;
import com.msu.scenarios.thief.bonyadi.BenchmarkSingleObjective;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.util.ThiefUtil;

public class BonyadiSingleExperiment extends ABonyadiBenchmark {

	final public static String FOLDER = "../ttp-benchmark/SingleObjective";

	final public static String PATTERN = "(\\d+)_(\\d+)_1_75";
	
	@Override
	protected void finalize() {
		new ObjectiveBoxPlot().show(this);
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
			
			String name = new File(path).getName().split("\\.")[0];
			Pattern r = Pattern.compile(PATTERN);
			
			// pattern has to match
			if (!r.matcher(name).matches())  continue;
			
			SingleObjectiveThiefProblem problem = new BenchmarkSingleObjective().create(path);
			problem.setName("SO_" + name);
			problems.add(problem);
		}
	}

}
