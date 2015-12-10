package com.msu.thief.evaluator.profit;

import java.util.Map;

import com.msu.thief.model.Item;
/**
 * The IndividualProfitCalculator calculates the profit by using a dropping rate for each item!
 */
public class IndividualProfitEvaluator extends ProfitEvaluator {

	
	public final static double DROPPING_RATE = 0.9;

	@Override
	public Double evaluate(Map<Item, Double> mItems) {
		Double profit = 0.0d;
		for (Item d : mItems.keySet()) {
			double profitEndOfTour = d.getProfit() * Math.pow(DROPPING_RATE , mItems.get(d) / d.getDropping());
			if (profitEndOfTour > 0) profit += profitEndOfTour;
		}
		return profit;
	}


}
