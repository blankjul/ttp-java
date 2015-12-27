package com.msu.thief.experiment;

import com.msu.model.Report;
import com.msu.moo.model.solution.Solution;
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
				Solution best = event.getNonDominatedSolutionSet().get(0);
				double value = (event.getNonDominatedSolutionSet().size() == 0) ? Double.NEGATIVE_INFINITY
						: -best.getObjectives(0);
				
				String var = best.getVariable().toString();
				var = var.replace(',', ' ');
				
				pw.printf("%s,%s,%s,%s\n", event.getProblem(), event.getAlgorithm(), value, var);
			}
		});
	}

}
