package com.msu.thief.experiment;

import com.msu.model.Report;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.RunFinishedEvent;

public class SingleObjectiveReport extends Report {

	public SingleObjectiveReport(String path) {
		super(path);
		pw.println("problem,algorithm,result");
		EventDispatcher.getInstance().register(RunFinishedEvent.class, new IListener<RunFinishedEvent>() {
			@Override
			public void handle(RunFinishedEvent event) {
				double value = (event.getNonDominatedSolutionSet().size() == 0) ? Double.NEGATIVE_INFINITY
						: -event.getNonDominatedSolutionSet().get(0).getObjectives(0);
				pw.printf("%s,%s,%s\n", event.getProblem(), event.getAlgorithm(), value);
			}
		});
	}

}
