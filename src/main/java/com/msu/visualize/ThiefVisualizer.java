package com.msu.visualize;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.ExperimentResult;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.plots.ScatterPlot;

public class ThiefVisualizer<P extends IProblem> implements IVisualize<P, NonDominatedSolutionSet> {

	protected boolean isColoredByTours = true;

	
	public ThiefVisualizer() {
		super();
	}

	public ThiefVisualizer(boolean isColoredByTours) {
		super();
		this.isColoredByTours = isColoredByTours;
	}


	@Override
	public void show(ExperimetSettings<P, NonDominatedSolutionSet> settings, ExperimentResult<NonDominatedSolutionSet> result) {
		for (IProblem problem : settings.getProblems()) {
			String title = problem.toString();
			ScatterPlot sp = (isColoredByTours) ? new ColoredTourScatterPlot(title) : new ScatterPlot(title);
			for (IAlgorithm<NonDominatedSolutionSet, ?> algorithm : settings.getAlgorithms()) {
				for (NonDominatedSolutionSet set : result.get(problem, algorithm)) {
					sp.add(set, algorithm.toString());
				}
			}
			sp.show();
		}
	}
}
