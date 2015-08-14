package com.moo.ttp.problems.ttp.profit;

import java.util.HashMap;
import java.util.List;

import com.moo.ttp.model.item.Item;

/**
 * The IndividualProfitCalculator calculates the profit by using a dropping rate for each item!
 */
public class ExponentialProfitCalculator implements ProfitCalculator{
	
	protected double droppingRate = 0.9;
	
	protected double droppingConstant = 10;

	@Override
	public <T extends Item> double run(List<T> items, HashMap<Integer, Double> pickingTimes) {
		double profit = 0;
		for (Integer index : pickingTimes.keySet()) {
			double itemValue = 0;
			itemValue = items.get(index).getProfit() * Math.pow(droppingRate, pickingTimes.get(index) / droppingConstant);
			profit += itemValue;
		}
		return profit;
	}

	
	
}
