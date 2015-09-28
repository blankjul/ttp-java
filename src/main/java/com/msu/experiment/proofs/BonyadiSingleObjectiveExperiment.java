package com.msu.experiment.proofs;

import java.util.List;

import com.msu.algorithms.ExhaustiveThief;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.report.SolutionSetReport;
import com.msu.scenarios.thief.bonyadi.PublicationScenarioSingleObjective;

public class BonyadiSingleObjectiveExperiment extends AExperiment {



	@Override
	protected void finalize() {
		new SolutionSetReport().print(this);
	}


	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		//algorithms.add(AlgorithmFactory.createNSGAII());
		algorithms.add(new ExhaustiveThief());
	}
	

	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.add(new PublicationScenarioSingleObjective().getObject());
	}

}
