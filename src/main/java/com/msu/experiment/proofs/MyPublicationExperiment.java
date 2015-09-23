package com.msu.experiment.proofs;

import com.msu.AlgorithmFactory;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.scenarios.thief.PublicationScenario;
import com.msu.thief.ThiefProblem;

public class MyPublicationExperiment extends AMultiObjectiveExperiment<ThiefProblem> {

	@Override
	protected void setAlgorithms(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		settings.addAlgorithm(AlgorithmFactory.createNSGAII());
	}
	
	@Override
	protected void setProblems(ExperimetSettings<ThiefProblem, NonDominatedSolutionSet> settings) {
		settings.addProblem(new PublicationScenario().getObject());
	}



}