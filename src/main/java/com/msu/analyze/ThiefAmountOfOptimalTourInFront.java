package com.msu.analyze;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.algorithms.exhaustive.SalesmanExhaustive;
import com.msu.algorithms.exhaustive.ThiefExhaustive;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.tour.Tour;

public class ThiefAmountOfOptimalTourInFront extends AbstractAnalyzer<ThiefProblem, Double> {

	@Override
	public Double analyze(ThiefProblem problem) {
		
		SolutionSet salesmanSet = new SalesmanExhaustive().run(new SalesmanProblem(problem.getMap())).getSolutions();
		salesmanSet.sortByObjective(0);
		
		Set<List<Integer>> hash = new HashSet<>();
		Tour<?> best = (Tour<?>) salesmanSet.get(0).getVariable();
		hash.add(best.encode());
		hash.add(best.getSymmetric().encode());
		
		NonDominatedSolutionSet set = new ThiefExhaustive().run(problem);
		
		int counter = 0;
		for (Solution s : set.getSolutions()) {
			Tour<?> var = ((TTPVariable)s.getVariable()).getTour();
			if (hash.contains(var.encode())) {
				++counter;
			}
		}
		return counter / (double) set.size();
	}
	
	
}
