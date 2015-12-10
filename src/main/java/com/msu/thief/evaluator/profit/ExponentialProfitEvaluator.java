package com.msu.thief.evaluator.profit;

import java.util.Map;

import com.msu.thief.model.Item;

/**
 * The ExponentialProfitCalculator uses the same function for all items.
 */
public class ExponentialProfitEvaluator extends ProfitEvaluator{
	
	//! dropping rate over time
	protected Double droppingRate;
	
	//! dropping constant for tuning the large distances
	protected Double droppingConstant;



	public ExponentialProfitEvaluator(double droppingRate, double droppingConstant) {
		super();
		this.droppingRate = droppingRate;
		this.droppingConstant = droppingConstant;
	}



	@Override
	public Double evaluate(Map<Item, Double> mItems) {
		Double profit = 0.0d;
		for (Item i : mItems.keySet()) {
			double timeInKnapsack = mItems.get(i) ;
			double itemValue = i.getProfit() * Math.pow(droppingRate,timeInKnapsack / droppingConstant);
			//System.out.println(String.format("%s %s %s", timeInKnapsack, i.getProfit(), itemValue));
			profit += itemValue;
		}
		return profit;
	}


	public double getDroppingRate() {
		return droppingRate;
	}


	public double getDroppingConstant() {
		return droppingConstant;
	}
	
	

	
	
}
