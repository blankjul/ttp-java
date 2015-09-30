package com.msu.experiment.proofs;

import java.util.List;

import com.google.common.collect.Multimap;
import com.msu.algorithms.ExhaustiveThief;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.report.SingleObjectiveReport;
import com.msu.moo.util.events.FinishedProblemExecution;
import com.msu.moo.util.events.IEvent;
import com.msu.moo.util.events.IListener;
import com.msu.scenarios.thief.bonyadi.PublicationScenarioSingleObjective;

public class BonyadiSingleObjectiveExperiment extends AExperiment {



	@Override
	protected void setListener(Multimap<Class<?>, IListener<? extends IEvent>> listener) {
		listener.put(FinishedProblemExecution.class, new SingleObjectiveReport());
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
