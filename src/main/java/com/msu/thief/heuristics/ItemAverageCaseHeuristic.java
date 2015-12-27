package com.msu.thief.heuristics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.interfaces.IEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;

public class ItemAverageCaseHeuristic extends ItemHeuristic{

	protected List<Double> weights;
	
	public ItemAverageCaseHeuristic(ThiefProblemWithFixedTour problem, IEvaluator evaluator) {
		super(problem, evaluator);
		
		weights = new ArrayList<>();
		double slope = problem.getMaxWeight() / (double) problem.numOfCities();
		for (int i = 0; i < problem.numOfCities(); i++) {
			weights.add((i+1) * slope);
		}
		
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
				double speedDiff = problem.getMaxSpeed() - problem.getMinSpeed();
				double speed = problem.getMaxSpeed() - weights.get(i) * speedDiff / problem.getMaxWeight();
				deltaTime += (problem.getMap().get(pi.get(i), pi.get((i + 1) % pi.size()) ) / speed);
			}
			
			double R = ((SingleObjectiveThiefProblem) problem.getProblem()).getR();
			double value = problem.getItems().get(idx).getProfit() - R * deltaTime;
			m.put(idx, value);
		}
		
		return m;
	}
	
	
	

}
