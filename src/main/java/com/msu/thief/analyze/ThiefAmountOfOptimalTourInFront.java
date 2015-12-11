package com.msu.thief.analyze;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.thief.algorithms.exhaustive.SalesmanExhaustive;
import com.msu.thief.algorithms.exhaustive.ThiefExhaustive;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class ThiefAmountOfOptimalTourInFront extends AbstractAnalyzer<AbstractThiefProblem, Double> {

	@Override
	public Double analyze(AbstractThiefProblem problem) {
		
		Evaluator eval = new Evaluator(Integer.MAX_VALUE);
		
		SolutionSet salesmanSet = new SalesmanExhaustive().run(new SalesmanProblem(problem.getMap()), eval, new MyRandom()).getSolutions();
		salesmanSet.sortByObjective(0);
		
		Set<List<Integer>> hash = new HashSet<>();
		Tour<?> best = (Tour<?>) salesmanSet.get(0).getVariable();
		hash.add(best.encode());
		hash.add(best.getSymmetric().encode());
		
		NonDominatedSolutionSet set = new ThiefExhaustive().run(problem, new Evaluator(Integer.MAX_VALUE), new MyRandom());
		
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
