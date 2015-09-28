package com.msu.experiment.bonyadi;

import java.io.File;
import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.scenarios.thief.bonyadi.BenchmarkMuliObjective;
import com.msu.thief.ThiefProblem;
import com.msu.util.Util;

public class BonyadiMultiExperiment extends ABonyadiBenchmark {
	
	final public static String FOLDER = "../ttp-benchmark/MultiObjective/10/10_3_1_25.txt";
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		for (String path : Util.getFiles(FOLDER)) {
			ThiefProblem ttp = new BenchmarkMuliObjective().create(path);
			ttp.setName("MO_" + new File(path).getName().split("\\.")[0]);
			problems.add(ttp);
		}
	}
	
	@Override
	protected void finalize() {
		new AttainmentSurfacePlot().show(this);
	}

	
}
