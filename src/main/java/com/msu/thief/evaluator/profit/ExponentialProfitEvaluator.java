package com.msu.thief.evaluator.profit;

import java.util.Map;

import com.msu.thief.model.Item;

/**
 * The ExponentialProfitCalculator uses the same function for all items.
 */
public class ExponentialProfitEvaluator extends ProfitEvaluator{
	
	//! dropping rate over time
	protected double droppingRate = 0.9;
	
	//! dropping constant for tuning the large distances
	protected double droppingConstant = 10;


	public ExponentialProfitEvaluator() {
		super();
	}


	public ExponentialProfitEvaluator(double droppingRate, double droppingConstant) {
		super();
		this.droppingRate = droppingRate;
		this.droppingConstant = droppingConstant;
	}



	@Override
	public Double evaluate(Map<Item, Double> mItems) {
		Double profit = 0.0d;
		for (Item i : mItems.keySet()) {
			 double itemValue = i.getProfit() * Math.pow(droppingRate, mItems.get(i) / droppingConstant);
			profit += itemValue;
		}
		return profit;
	}

	
	
}
