package com.msu.thief.evaluator.profit;

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
	protected double calcProfit(Item item, double timeInKnapsack) {
		return item.getProfit() * Math.pow(droppingRate, timeInKnapsack / droppingConstant);
	}
	

	public double getDroppingRate() {
		return droppingRate;
	}


	public double getDroppingConstant() {
		return droppingConstant;
	}




	
	
}
