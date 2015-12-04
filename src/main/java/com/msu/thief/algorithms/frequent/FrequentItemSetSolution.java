package com.msu.thief.algorithms.frequent;

import java.util.List;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.apriori.AprioriEntry;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;

public class FrequentItemSetSolution extends Solution {

	
	
	private FrequentItemSetSolution(IVariable variable, List<Double> objectives) {
		super(variable, objectives);
	}


	public boolean singleObjective = true;
	
	Solution single;
	Solution multi;
	
	public Set<Integer> items;
	
	
	public static FrequentItemSetSolution create(IEvaluator eval, SingleObjectiveThiefProblem problem, Tour<?> tour, Set<Integer> items) {
		
		PackingList<?> b = new BooleanPackingList(items, problem.numOfItems());
		FrequentItemSetSolution s = new FrequentItemSetSolution(new TTPVariable(tour,b), null);
		
		s.items = items;
		s.single = eval.evaluate(problem, new TTPVariable(tour, b));
		
		problem.setToMultiObjective(true);
		s.multi = eval.evaluate(problem, new TTPVariable(tour, b));
		problem.setToMultiObjective(false);
		
		return s;
	}


	@Override
	public Double getObjectives(int n) {
		if (singleObjective) return single.getObjectives(n);
		else return multi.getObjectives(n);
	}


	@Override
	public List<Double> getObjective() {
		if (singleObjective) return single.getObjective();
		else return multi.getObjective();
	}
	
	
	
	
	@Override
	public List<Double> getConstraintViolations() {
		if (singleObjective) return single.getConstraintViolations();
		else return multi.getConstraintViolations();
	}


	@Override
	public int hashCode() {
		return items.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return items.equals(((FrequentItemSetSolution)obj).items);
	}


	@Override
	public boolean hasConstrainViolations() {
		if (singleObjective) return single.hasConstrainViolations();
		else return multi.hasConstrainViolations();
	}


	@Override
	public Double getMaxConstraintViolation() {
		if (singleObjective) return single.getMaxConstraintViolation();
		else return multi.getMaxConstraintViolation();
	}


	@Override
	public Double getSumOfConstraintViolation() {
		if (singleObjective) return single.getSumOfConstraintViolation();
		else return multi.getSumOfConstraintViolation();
	}
	
	
	
	
	

	
	

}
