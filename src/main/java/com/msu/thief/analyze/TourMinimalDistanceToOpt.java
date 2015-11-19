package com.msu.thief.analyze;


import com.msu.model.Evaluator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.thief.algorithms.exhaustive.SalesmanExhaustive;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.util.Random;

public class TourMinimalDistanceToOpt extends AbstractAnalyzer<SalesmanProblem, Double> {

	
	
	@Override
	public Double analyze(SalesmanProblem tsp) {
		SolutionSet s = new SalesmanExhaustive().setOnlyNonDominatedPoints(false)
				.run(tsp, new Evaluator(Integer.MAX_VALUE), new Random()).getSolutions();
		
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
