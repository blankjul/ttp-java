package com.msu.experiment;

import com.msu.moo.experiment.OneProblemOneAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.visualization.ScatterPlot;
import com.msu.thief.AlgorithmFactory;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.scenarios.PublicationScenario;

public class PublicationExperiment extends OneProblemOneAlgorithmExperiment<TravellingThiefProblem> {

	@Override
	public void report() {
		ScatterPlot sp = new ScatterPlot("GreedyMap");
		sp.show();
	}

	@Override
	protected IAlgorithm<TravellingThiefProblem> getAlgorithm() {
		return AlgorithmFactory.createNSGAII();
	}

	@Override
	protected TravellingThiefProblem getProblem() {
		return new PublicationScenario().getObject();
	}



}