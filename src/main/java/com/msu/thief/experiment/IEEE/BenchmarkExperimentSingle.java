package com.msu.thief.experiment.IEEE;

import java.util.List;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.callback.ICallback;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.FileCollectorParser;
import com.msu.thief.algorithms.impl.ThiefEvolutionaryAlgorithm;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.io.thief.reader.JsonThiefProblemReader;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class BenchmarkExperimentSingle extends AExperiment<Solution<TTPVariable>, TTPVariable, AbstractThiefProblem> {


	@Override
	protected void setAlgorithms(AbstractThiefProblem problem,
			List<IAlgorithm<Solution<TTPVariable>, TTPVariable, AbstractThiefProblem>> algorithms) {
		algorithms.add(new ThiefEvolutionaryAlgorithm());
	}


	@Override
	protected void setProblems(List<AbstractThiefProblem> problems) {
		FileCollectorParser<AbstractThiefProblem> fcp = new FileCollectorParser<>();
		fcp.add("../new/", "single*", new JsonThiefProblemReader());
		List<AbstractThiefProblem> l = fcp.collect();
		for (AbstractThiefProblem p : l) {
			problems.add((AbstractThiefProblem)p);
		}
	}



	@Override
	protected ICallback<Solution<TTPVariable>, TTPVariable, AbstractThiefProblem> getCallback() {
		return new ICallback<Solution<TTPVariable>, TTPVariable, AbstractThiefProblem>() {
			@Override
			public void analyze(AbstractThiefProblem problem,
					IAlgorithm<Solution<TTPVariable>, TTPVariable, AbstractThiefProblem> algorithm, int run,
					Solution<TTPVariable> result) {
				
				MultiObjectiveThiefProblem thief = new MultiObjectiveThiefProblem(problem);
				Tour pi = AlgorithmUtil.calcBestTour(thief);
				Pack z = AlgorithmUtil.calcBestPackingPlan(thief.getItems(), thief.getMaxWeight());

				Solution<TTPVariable> bestPi = thief.evaluate(new TTPVariable(pi, Pack.empty()));
				Solution<TTPVariable> bestPiBestZ = thief.evaluate(new TTPVariable(pi, z));
				double deltaTime = bestPiBestZ.getObjective(0) - bestPi.getObjective(0);
				
				System.out.println(String.format("%s,%s,%s,%s", problem, algorithm, - result.getObjective(0), result.getObjective(0) + bestPi.getObjective(0)));
			}
		};
	}




}