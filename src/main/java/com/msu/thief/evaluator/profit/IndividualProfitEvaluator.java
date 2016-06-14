package com.msu.thief.evaluator.profit;

import com.msu.thief.model.Item;
/**
 * The IndividualProfitCalculator calculates the profit by using a dropping rate for each item!
 */
public class IndividualProfitEvaluator extends ProfitEvaluator {

	
	public final static double DROPPING_RATE = 0.9;



	@Override
	protected double calcProfit(Item item, double timeInKnapsack) {
		return item.getProfit() * Math.pow(DROPPING_RATE , timeInKnapsack / item.getDropping());
	}
	



}
