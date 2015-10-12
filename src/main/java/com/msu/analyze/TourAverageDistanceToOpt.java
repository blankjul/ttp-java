package com.msu.analyze;

import com.msu.algorithms.exhaustive.SalesmanExhaustive;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.problems.SalesmanProblem;

public class TourAverageDistanceToOpt extends AbstractAnalyzer<SalesmanProblem, Double> {

	@Override
	public Double analyze(SalesmanProblem tsp) {
		
		SolutionSet set = new SalesmanExhaustive().setOnlyNonDominatedPoints(false).run(tsp).getSolutions();
		set.sortByObjective(0);
		double min = set.get(0).getObjectives(0);
		
		
		double sum = 0;
		for (Solution s : set) {
			double value = s.getObjectives(0);
			sum += (value - min) / min;
		}
		
		return sum / tsp.numOfCities();
	}
}
