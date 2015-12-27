package com.msu.thief.heuristics;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.interfaces.IEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;

public class ItemWorstCaseHeuristic extends ItemHeuristic{

	public ItemWorstCaseHeuristic(ThiefProblemWithFixedTour problem, IEvaluator evaluator) {
		super(problem, evaluator);
	}

	@Override
	public Map<Integer, Double> calc(Collection<Integer> c) {
		
		Map<Integer, Double> m = new HashMap<>();
		ItemCollection<Item> items = problem.getProblem().getItemCollection();
		List<Integer> pi = problem.getTour().encode();
		
		
		for (Integer idx : c) {
			
			int cityOfPick = items.getCityOfItem(idx);
			int startIdx = pi.indexOf(cityOfPick);
			
			double deltaTime = 0;
			
			for (int i = startIdx; i < pi.size(); i++) {
				deltaTime += (problem.getMap().get(pi.get(i), pi.get((i + 1) % pi.size()) ) / 0.1);
			}
			
			double R = ((SingleObjectiveThiefProblem) problem.getProblem()).getR();
			double value = problem.getItems().get(idx).getProfit() - R * deltaTime;
			m.put(idx, value);
		}
		
		return m;
	}
	
	
	

}
