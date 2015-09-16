package com.msu.experiment;

import com.msu.algorithms.AlgorithmFactory;
import com.msu.moo.experiment.OneProblemOneAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.scenarios.impl.PublicationScenario;

public class PublicationExperiment extends OneProblemOneAlgorithmExperiment<TravellingThiefProblem> {


	@Override
	protected IAlgorithm<TravellingThiefProblem> getAlgorithm() {
		return AlgorithmFactory.createNSGAII();
	}

	@Override
	protected TravellingThiefProblem getProblem() {
		return new PublicationScenario().getObject();
	}



}