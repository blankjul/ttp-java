package com.msu.visualize;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentResult;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.plots.ScatterPlot;

public class ThiefVisualizer<P extends IProblem> implements IVisualize {

	protected boolean isColoredByTours = true;

	public ThiefVisualizer() {
		super();
	}

	public ThiefVisualizer(boolean isColoredByTours) {
		super();
		this.isColoredByTours = isColoredByTours;
	}

	@Override
	public void show(AExperiment experiment) {
		ExperimentResult result = experiment.getResult();
		for (IProblem problem : experiment.getProblems()) {
			String title = problem.toString();
			ScatterPlot sp = (isColoredByTours) ? new ColoredTourScatterPlot(title) : new ScatterPlot(title);
			for (IAlgorithm algorithm : experiment.getAlgorithms()) {
				for (NonDominatedSolutionSet set : result.get(problem, algorithm)) {
					sp.add(set, algorithm.toString());
				}
			}
			if (experiment.hasOutputDirectory()) sp.save(String.format("%s/thief_%s.png", experiment.getOutputDir(), problem));
			if (experiment.isVisualize()) sp.show();
		}

	}
}
