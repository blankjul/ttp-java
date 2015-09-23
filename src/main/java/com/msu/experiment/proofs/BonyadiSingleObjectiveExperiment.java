package com.msu.experiment.proofs;

import com.msu.algorithms.ExhaustiveThief;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.scenarios.thief.bonyadi.PublicationScenarioSingleObjective;
import com.msu.thief.ThiefProblem;

public class BonyadiSingleObjectiveExperiment extends AMultiObjectiveExperiment<ThiefProblem> {

	
	
	@Override
	public void visualize() {
	}


	@Override
	protected void report_(ThiefProblem problem, IAlgorithm<NonDominatedSolutionSet, ThiefProblem> algorithm, NonDominatedSolutionSet set) {
		System.out.println(String.format("%s,%s,%s", problem, algorithm, set.getSolutions().get(0)));
	}


	@Override
	protected void setAlgorithms(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		//settings.addAlgorithm(AlgorithmFactory.createNSGAII());
		settings.addAlgorithm(new ExhaustiveThief());
	}
	

	@Override
	protected void setProblems(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
			settings.addProblem(new PublicationScenarioSingleObjective().getObject());
	}

}
