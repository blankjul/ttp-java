package com.msu.experiment;

import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.scenarios.thief.bonyadi.BenchmarkTSPLIB;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.thief.ThiefProblem;

public class BonyadiTSPLIBExperiment extends ABonyadiBenchmark {

	final public static String[] INSTANCES = new String[] { 
			"../ttp-benchmark/TSPLIB/rat195-ttp/rat195_n582_bounded-strongly-corr_03.ttp",
			//"../ttp-benchmark/10/10_15_10_75.txt",
			//"../ttp-benchmark/20/20_25_10_75.txt",
			//"../ttp-benchmark/50/50_75_10_75.txt",
			//"../ttp-benchmark/100/100_150_10_75.txt",
	};
	

	@Override
	protected void setProblems(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		for(String s : INSTANCES) {
			SingleObjectiveThiefProblem ttp = new BenchmarkTSPLIB().create(s);
			ttp.setName(s);
			settings.addProblem(ttp);
		}
	}

}
