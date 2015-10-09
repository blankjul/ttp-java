package com.msu.analyze;

import com.msu.algorithms.exhaustive.SalesmanExhaustive;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.problems.ThiefProblem;
import com.msu.problems.SalesmanProblem;

public class TSPTourDominance extends AbstractAnalyzer<ThiefProblem, Double> {

	@Override
	public Double analyze(ThiefProblem p) {
		SalesmanProblem tsp = new SalesmanProblem(p.getMap());
		NonDominatedSolutionSet set = new SalesmanExhaustive().setOnlyNonDominatedPoints(false).run(tsp);
		
		double min = Double.MAX_VALUE;
		for (Solution s : set.getSolutions()) {
			double value = s.getObjectives(0);
			if (value < min) min = value;
		}
		
		double sum = 0;
		for (Solution s : set.getSolutions()) {
			double value = s.getObjectives(0);
			sum += (value - min) / min;
		}
		
		return sum / p.numOfCities();
	}
}
