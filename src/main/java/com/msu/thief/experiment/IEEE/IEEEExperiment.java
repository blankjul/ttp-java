package com.msu.thief.experiment.IEEE;

import java.util.List;

import com.msu.moo.experiment.ASingleObjectiveExperiment;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.impl.ThiefBestOfMultiObjectiveFront;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class IEEEExperiment extends ASingleObjectiveExperiment<TTPVariable, SingleObjectiveThiefProblem> {

	
	@Override
	protected void setAlgorithms(SingleObjectiveThiefProblem problem,
			List<IAlgorithm<Solution<TTPVariable>, TTPVariable, SingleObjectiveThiefProblem>> algorithms) {
		algorithms.add(new ThiefBestOfMultiObjectiveFront());
	}



	@Override
	protected void setProblems(List<SingleObjectiveThiefProblem> problems) {
		problems.addAll(IEEE.getProblems());
	}

	



}