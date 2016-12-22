package com.msu.thief.experiment;

import java.util.List;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentCallback;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.util.FileCollectorParser;
import com.msu.thief.algorithms.ThiefOnePlusOneEA;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class BenchmarkExperimentSingleObjective extends AExperiment<ISolution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem> {


	@Override
	protected void setAlgorithms(SingleObjectiveThiefProblem problem,
			List<IAlgorithm<ISolution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem>> algorithms) {
		algorithms.add(new ThiefOnePlusOneEA());
		//algorithms.add(new ThiefRandomLocalSearch());
	}


	@Override
	protected void setProblems(List<SingleObjectiveThiefProblem> problems) {
		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../ttp-benchmark-other/TSPLIB/berlin52-ttp/", "berlin52_n51_uncorr-similar-weights_03*", new ThiefSingleTSPLIBProblemReader());
		fcp.collect().forEach(p -> problems.add((SingleObjectiveThiefProblem)p));
	}



	@Override
	protected void analyse(ExperimentCallback<ISolution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem> callback) {
		System.out.println(String.format("%s,%s,%s", callback.problem,callback. algorithm, - callback.result.getObjective(0)));
	}




}