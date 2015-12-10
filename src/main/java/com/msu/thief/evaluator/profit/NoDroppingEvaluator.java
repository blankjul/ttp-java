package com.msu.thief.evaluator.profit;

import com.msu.thief.model.Item;

/**
 * The IndividualProfitCalculator calculates the profit by using a dropping rate
 * for each item!
 */
public class NoDroppingEvaluator extends ProfitEvaluator {

	@Override
	protected double calcProfit(Item item, double timeInKnapsack) {
		return item.getProfit();
	}

}
