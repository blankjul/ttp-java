package com.msu.thief.evaluator.profit;

import java.util.Map;

import com.msu.knp.model.Item;
/**
 * The IndividualProfitCalculator calculates the profit by using a dropping rate for each item!
 */
public class IndividualProfitEvaluator extends ProfitEvaluator {


	@Override
	public Double evaluate(Map<Item, Double> mItems) {
		Double profit = 0.0d;
		for (Item d : mItems.keySet()) {
			double profitEndOfTour = d.getProfit() - d.getDropping() * mItems.get(d);
			if (profitEndOfTour > 0) profit += profitEndOfTour;
		}
		return profit;
	}


}
