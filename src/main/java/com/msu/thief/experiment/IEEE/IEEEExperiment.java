package com.msu.thief.experiment.IEEE;

import java.util.List;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.callback.ICallback;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.impl.ThiefBankToBankHillClimbing;
import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class IEEEExperiment extends AExperiment<Solution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem> {



	@Override
	protected void setAlgorithms(SingleObjectiveThiefProblem problem,
			List<IAlgorithm<Solution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem>> algorithms) {
		//algorithms.add(new ThiefTwoPhaseEvolution());
		algorithms.add(new ThiefBankToBankHillClimbing());
		//algorithms.add(new ThiefOnePlusOneEA());
		//algorithms.add(new ThiefEvolutionaryAlgorithm());
		
	}



	@Override
	protected void setProblems(List<SingleObjectiveThiefProblem> problems) {
		//problems.addAll(IEEE.getProblems());
		//problems.addAll(IEEE.getTTPLIBProblems());
		problems.add(new ThiefSingleTSPLIBProblemReader().read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n255_bounded-strongly-corr_01.ttp"));
		//problems.add(new ThiefSingleTSPLIBProblemReader().read("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp"));
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