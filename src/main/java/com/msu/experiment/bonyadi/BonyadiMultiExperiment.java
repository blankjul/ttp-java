package com.msu.experiment.bonyadi;

import java.io.File;
import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.scenarios.thief.bonyadi.BenchmarkMuliObjective;
import com.msu.thief.ThiefProblem;
import com.msu.util.ThiefUtil;

public class BonyadiMultiExperiment extends ABonyadiBenchmark {
	
	final public static String FOLDER = "../ttp-benchmark/MultiObjective/100/100_150_10_75.txt";
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		for (String path : ThiefUtil.getFiles(FOLDER)) {
			ThiefProblem ttp = new BenchmarkMuliObjective().read(path);
			ttp.setName("MO_" + new File(path).getName().split("\\.")[0]);
			problems.add(ttp);
		}
	}
	
	@Override
	protected void finalize() {
		new AttainmentSurfacePlot().setVisibility(true);
	}

	
}
