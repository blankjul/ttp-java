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
	protected Double dropping = null;
	

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

	public Double getDropping() {
		return dropping;
	}
	
	
	public void setDropping(double dropping) {
		this.dropping = dropping;
	}


	public String toString() {
		if (dropping == null) return String.format("[p:%s | w:%s]", profit,weight);
		return String.format("[p:%s | w:%s | d:%s]", profit,weight, dropping);
	}
	
	
	


}
