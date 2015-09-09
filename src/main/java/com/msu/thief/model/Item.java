package com.msu.thief.model;

/**
 * This class represents an item that could be picked by the salesman or
 * selected for the knapsack. The attributes are the weight and profit.
 */
public class Item {
	
	//! weight of the item
	protected int weight;
	
	//! profit of the item
	protected int profit;
	
	//! dropping of the item over time
	protected double dropping;
	

	/**
	 * Create an item with predefined values.
	 */
	public Item(int profit, int weight) {
		super();
		this.weight = weight;
		this.profit = profit;
	}
	

	/**
	 * Create an item with profit,weight and dropping.
	 */
	public Item(int profit, int weight, double dropping) {
		this(profit, weight);
		this.dropping = dropping;
	}
	
	

	public int getWeight() {
		return weight;
	}

	public int getProfit() {
		return profit;
	}


	public double getDropping() {
		return dropping;
	}
	
	public String toString() {
		return String.format("w:%s|v:%s|d:%s", weight, profit, dropping);
	}
	
	
	


}
