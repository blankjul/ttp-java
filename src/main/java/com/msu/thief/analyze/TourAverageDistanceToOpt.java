package com.msu.thief.analyze;

import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.thief.algorithms.exhaustive.SalesmanExhaustive;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.util.MyRandom;

public class TourAverageDistanceToOpt extends AbstractAnalyzer<SalesmanProblem, Double> {

	@Override
	public Double analyze(SalesmanProblem tsp) {
		Evaluator eval = new Evaluator(Integer.MAX_VALUE);
		SolutionSet set = new SalesmanExhaustive().setOnlyNonDominatedPoints(false).run(tsp, eval, new MyRandom()).getSolutions();
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
