package com.msu.thief.evaluator.profit;

import java.util.Map;

import com.msu.thief.model.Item;
/**
 * The IndividualProfitCalculator calculates the profit by using a dropping rate for each item!
 */
public class NoDroppingEvaluator extends ProfitEvaluator {


	@Override
	public Double evaluate(Map<Item, Double> mItems) {
		Double profit = 0.0d;
		for (Item d : mItems.keySet()) {
			profit += d.getProfit();
		}
		return profit;
	}


}
