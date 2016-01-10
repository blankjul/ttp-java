package com.msu.thief.heuristics;

import java.util.Collection;
import java.util.HashMap;
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
		
		IntegerSetPackingList b =  new IntegerSetPackingList(problem.numOfItems());
		Solution before = evaluator.evaluate(problem,b);
		
		Map<Integer, Double> m = new HashMap<>();
		
		
		for (Integer idx : c) {
			
			b.get().first.add(idx);
			Solution current = evaluator.evaluate(problem,b);
			b.get().first.remove(idx);
			
			Double value = before.getObjectives(0) - current.getObjectives(0);
			m.put(idx, value);
		}
		
		return m;
	}
	
	
	

}
