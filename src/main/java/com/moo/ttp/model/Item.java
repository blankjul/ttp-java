package com.moo.ttp.model;

/**
 * This class represents an item that could be picked by the salesman or
 * selected for the knapsack. The attributes are the weight and profit.
 */
public class Item {
	
	//! weight of the item
	protected int weight;
	
	//! profit of the item
	protected int profit;
	

	/**
	 * Create an item with predefined values.
	 */
	public Item(int profit, int weight) {
		super();
		this.weight = weight;
		this.profit = profit;
	}
	

	public int getWeight() {
		return weight;
	}

	public int getProfit() {
		return profit;
	}
	


}
