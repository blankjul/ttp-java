package com.msu.analyze;

import com.msu.algorithms.ExhaustiveSalesman;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.ThiefProblem;
import com.msu.tsp.TravellingSalesmanProblem;

public class TSPTourDominance extends AThiefProblemAnalyzer<ThiefProblem, Double> {

	@Override
	public Double analyze(ThiefProblem p) {
		TravellingSalesmanProblem tsp = new TravellingSalesmanProblem(p.getMap());
		NonDominatedSolutionSet set = new ExhaustiveSalesman().setOnlyNonDominatedPoints(false).run(tsp);
		
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
