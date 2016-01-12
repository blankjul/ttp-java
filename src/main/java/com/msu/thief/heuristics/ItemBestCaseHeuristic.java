package com.msu.thief.heuristics;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.IntegerSetPackingList;

public class ItemBestCaseHeuristic extends ItemHeuristic{

	public ItemBestCaseHeuristic(ThiefProblemWithFixedTour problem, IEvaluator evaluator) {
		super(problem, evaluator);
	}

	
	
	@Override
	public Map<Integer, Double> calc(Collection<Integer> c) {
		
		Solution before = evaluator.evaluate(problem,new IntegerSetPackingList(problem.numOfItems()));
		
		Map<Integer, Double> m = new HashMap<>();
		
		
		for (Integer idx : c) {
			
			Solution current = evaluator.evaluate(problem,new IntegerSetPackingList(new HashSet<>(Arrays.asList(idx)), problem.numOfItems()));
			
			Double value = before.getObjectives(0) - current.getObjectives(0);
			
			// double weight = problem.getItems().get(idx).getWeight();
			
			m.put(idx, value);
		}
		
		return m;
	}
	
	
	

}
