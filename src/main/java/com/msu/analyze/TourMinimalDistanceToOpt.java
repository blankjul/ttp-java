package com.msu.analyze;

import com.msu.algorithms.exhaustive.SalesmanExhaustive;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.problems.SalesmanProblem;

public class TourMinimalDistanceToOpt extends AbstractAnalyzer<SalesmanProblem, Double> {

	
	
	@Override
	public Double analyze(SalesmanProblem tsp) {
		SolutionSet s = new SalesmanExhaustive().setOnlyNonDominatedPoints(false).run(tsp).getSolutions();
		
		s.sortByObjective(0);
		
		double best = s.get(0).getObjectives(0);
		double secondBest = best;
		for (int i = 1; i < s.size(); i++) {
			double time = s.get(i).getObjectives(0);
			if (time > secondBest) {
				secondBest = time;
				break;
			}
		}
		
		return (best - secondBest) / best;
	}
	
	
	
}
