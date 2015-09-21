package com.msu.experiment;

import com.msu.AlgorithmFactory;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.scenarios.impl.PublicationScenario;

public class PublicationExperiment extends AMultiObjectiveExperiment<TravellingThiefProblem> {

	@Override
	protected void setAlgorithms(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		settings.addAlgorithm(AlgorithmFactory.createNSGAII());
	}
	
	@Override
	protected void setProblems(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		settings.addProblem(new PublicationScenario().getObject());
		
	}



}