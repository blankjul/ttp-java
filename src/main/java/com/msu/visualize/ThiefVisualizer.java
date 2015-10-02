package com.msu.visualize;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.IListener;
import com.msu.moo.util.events.ProblemFinishedEvent;
import com.msu.moo.util.plots.ScatterPlot;

public class ThiefVisualizer<P extends IProblem> extends AVisualize implements IListener<ProblemFinishedEvent>{

	protected boolean isColoredByTours = true;

	public ThiefVisualizer() {
		super();
		EventDispatcher.getInstance().register(ProblemFinishedEvent.class, this);
	}

	public ThiefVisualizer(boolean isColoredByTours) {
		this();
		this.isColoredByTours = isColoredByTours;
	}

	@Override
	public void update(ProblemFinishedEvent event) {
		AExperiment experiment = event.getExperiment();
		String title = event.getProblem().toString();
		
		ScatterPlot sp = (isColoredByTours) ? new ColoredTourScatterPlot(title) : new ScatterPlot(title);
		for (IAlgorithm algorithm : experiment.getAlgorithms()) {
			for (NonDominatedSolutionSet set : experiment.getResult().get(event.getProblem(), algorithm)) {
				sp.add(set, algorithm.toString());
			}
		}
		showOrPrint(sp, title);
	}


}
