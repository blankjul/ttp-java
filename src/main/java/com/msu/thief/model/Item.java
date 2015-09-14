package com.msu.thief.model;

/**
 * This class represents an item that could be picked by the salesman or
 * selected for the knapsack. The attributes are the weight and profit.
 */
public class Item {
	
	//! weight of the item
	protected double weight;
	
	//! profit of the item
	protected double profit;
	
	//! dropping of the item over time
	protected double dropping;
	

	/**
	 * Create an item with predefined values.
	 */
	public Item(double profit, double weight) {
		super();
		this.weight = weight;
		this.profit = profit;
	}
	

	/**
	 * Create an item with profit,weight and dropping.
	 */
	public Item(double profit, double weight, double dropping) {
		this(profit, weight);
		this.dropping = dropping;
	}
	
	

	public double getWeight() {
		return weight;
	}

	public double getProfit() {
		return profit;
	}


	public double getDropping() {
		return dropping;
	}
	
	public String toString() {
		return String.format("w:%s|v:%s|d:%s", weight, profit, dropping);
	}
	
	
	


}
