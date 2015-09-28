package com.msu.experiment.bonyadi;

import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.scenarios.thief.bonyadi.BenchmarkTSPLIB;
import com.msu.thief.SingleObjectiveThiefProblem;

public class BonyadiMultiExperiment extends ABonyadiBenchmark {

	final public static String[] INSTANCES = new String[] { 
			"../ttp-benchmark/10/10_15_10_75.txt",
			//"../ttp-benchmark/20/20_25_10_75.txt",
			//"../ttp-benchmark/50/50_75_10_75.txt",
			//"../ttp-benchmark/100/100_150_10_75.txt",
	};


	@Override
	protected void setProblems(List<IProblem> problems) {
		for(String s : INSTANCES) {
			SingleObjectiveThiefProblem ttp = new BenchmarkTSPLIB().create(s);
			ttp.setName(s);
			problems.add(ttp);
		}
	}

}
