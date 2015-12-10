package com.msu.thief.algorithms.bilevel.apriori;

import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;

public class AprioriEntry {
	public Solution solution;
	public double time;
	public double profit;
	public int idx;
	public Set<Integer> items;
	
	public AprioriEntry(int idx, Set<Integer> items) {
		this.idx = idx;
		this.items = items;
	}

	public Solution evaluate(IEvaluator eval, ThiefProblemWithFixedTour problem) {
		if (solution == null) {
			PackingList<?> b = new BooleanPackingList(items, problem.numOfItems());
			this.solution = eval.evaluate(problem, b);
			
			problem.setToMultiObjective(true);
			Solution multi = problem.evaluate(b);
			time = multi.getObjectives(0);
			profit = multi.getObjectives(1);
			problem.setToMultiObjective(false);
			
		}
		return solution;
	}

	@Override
	public int hashCode() {
		return items.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return items.equals(((AprioriEntry)obj).items);
	}
	
	
	
	
}