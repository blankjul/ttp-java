package com.msu.thief.evaluator.profit;

import java.util.ArrayList;
import java.util.List;

import com.msu.thief.evaluator.ItemInformation;
import com.msu.thief.evaluator.PackingInformation;
import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.model.Item;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.Tour;

/**
 * The ProfitCalculator provides an interface for calculating the profit. For
 * each item the time how long it was inside of the knapsack has to be provided.
 */
public abstract class ProfitEvaluator  {
	
	public double evaluate(AbstractThiefProblem problem, Tour tour, Pack pack, TourInformation info) {
		return evaluate_(problem, tour, pack, info).getProfit();
	}
	
	public PackingInformation evaluate_(AbstractThiefProblem problem, Tour tour, Pack pack, TourInformation info) {
		double profit = 0;
		double weight = 0;
		
		List<ItemInformation> items = new ArrayList<>();

		for (Integer idx : pack.toIndexSet()) {
			Item item = problem.getItem(idx);
			weight += item.getWeight();
			
			double timeInKnapsack = ItemInformation.calcTimeOfItemInKnapsack(problem, info, idx);
			
			double profitEndOfTour = calcProfit(item, timeInKnapsack);
			
			profit += profitEndOfTour;
			items.add(new ItemInformation(problem, info, idx, item.getProfit(), profitEndOfTour));
		}
		return new PackingInformation(profit, weight, items);
	};

	
	protected abstract double calcProfit(Item item, double timeInKnapsack);
	

}
