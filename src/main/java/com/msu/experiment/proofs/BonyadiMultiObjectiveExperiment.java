package com.msu.experiment.proofs;

import com.msu.algorithms.ExhaustiveThief;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.scenarios.thief.bonyadi.PublicationScenario;
import com.msu.thief.ThiefProblem;

public class BonyadiMultiObjectiveExperiment extends AMultiObjectiveExperiment<ThiefProblem> {

	
	
	@Override
	public void visualize() {
	}


	@Override
	protected void report_(ThiefProblem problem, IAlgorithm<NonDominatedSolutionSet, ThiefProblem> algorithm, NonDominatedSolutionSet set) {
		System.out.println(set);
	}


	@Override
	protected void setAlgorithms(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		settings.addAlgorithm(new ExhaustiveThief());
	}
	

	@Override
	protected void setProblems(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
			settings.addProblem(new PublicationScenario().getObject());
	}

}
