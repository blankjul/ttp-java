package com.msu.experiment.proofs;

import java.util.List;

import com.msu.AlgorithmFactory;
import com.msu.io.writer.JsonThiefProblemWriter;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.scenarios.thief.bonyadi.PublicationScenario;

public class MyPublicationExperiment extends AExperiment {

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(AlgorithmFactory.createNSGAII());
	}
	
	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.add(new PublicationScenario().getObject());
		
	}

	public static void main(String[] args) {
		new JsonThiefProblemWriter().write(new PublicationScenario().getObject(), "resources/bonyadi_publication.ttp");;
	}


}