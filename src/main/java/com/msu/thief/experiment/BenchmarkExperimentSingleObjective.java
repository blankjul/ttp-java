package com.msu.thief.experiment;

import java.util.List;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.callback.ICallback;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.FileCollectorParser;
import com.msu.thief.algorithms.ThiefRandomLocalSearch;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class BenchmarkExperimentSingleObjective extends AExperiment<Solution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem> {


	@Override
	protected void setAlgorithms(SingleObjectiveThiefProblem problem,
			List<IAlgorithm<Solution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem>> algorithms) {
		algorithms.add(new ThiefRandomLocalSearch());
	}


	@Override
	protected void setProblems(List<SingleObjectiveThiefProblem> problems) {
		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../json-single-objective/", "*", new JsonThiefProblemReader());
		fcp.collect().forEach(p -> problems.add((SingleObjectiveThiefProblem)p));
	}



	@Override
	protected ICallback<Solution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem> getCallback() {
		return new ICallback<Solution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem>() {
			@Override
			public void analyze(SingleObjectiveThiefProblem problem,
					IAlgorithm<Solution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem> algorithm, int run,
					Solution<TTPVariable> result) {
				System.out.println(String.format("%s,%s,%s", problem, algorithm, - result.getObjective(0)));
			}
		};
	}




}