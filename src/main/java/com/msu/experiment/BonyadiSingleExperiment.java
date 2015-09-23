package com.msu.experiment;

import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.scenarios.thief.bonyadi.BenchmarkSingleObjective;
import com.msu.thief.SingleObjectiveThiefProblem;
import com.msu.thief.ThiefProblem;

public class BonyadiSingleExperiment extends ABonyadiBenchmark {

	final public static String PREFIX = "../ttp-benchmark/SingleObjective";
	
	final public static String[] INSTANCES = new String[] { 
			"10_3_1_25",
			"100_10_8_75",
	};
	

	@Override
	protected void setProblems(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		for(String name : INSTANCES) {
			String s = getPath(PREFIX, name);
			SingleObjectiveThiefProblem ttp = new BenchmarkSingleObjective().create(s);
			ttp.setName(name);
			settings.addProblem(ttp);
		}
	}

}
