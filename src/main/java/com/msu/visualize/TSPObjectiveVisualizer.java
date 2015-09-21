package com.msu.visualize;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.ExperimentResult;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class TSPObjectiveVisualizer<P extends IProblem> implements IVisualize<P, NonDominatedSolutionSet> {


	@Override
	public void show(ExperimetSettings<P, NonDominatedSolutionSet> settings, ExperimentResult<NonDominatedSolutionSet> result) {
		for (IProblem problem : settings.getProblems()) {
			ColoredTourScatterPlot sp = new ColoredTourScatterPlot(problem.toString());
			sp.showLabels = false;
			for (IAlgorithm<NonDominatedSolutionSet, ?> algorithm : settings.getAlgorithms()) {
				for (NonDominatedSolutionSet set : result.get(problem, algorithm)) {
					sp.add(set, algorithm.toString());
				}
			}
			sp.show();
		}
	}
}
