package com.msu.thief.evaluator;

import java.util.List;

public class PackingInformation {
	
	//! the final sum of profit which was made
	protected double profit;
	
	//! the final sum of weight
	protected double weight;
	
	//! information of every item
	protected List<ItemInformation> itemsInfo;
	
	
	public PackingInformation(double profit, double weight, List<ItemInformation> itemsInfo) {
		super();
		this.profit = profit;
		this.weight = weight;
		this.itemsInfo = itemsInfo;
	}


	public ItemInformation getItemInformation(int idx) {
		return itemsInfo.get(idx);
	}


	public double getProfit() {
		return profit;
	}

	public double getWeight() {
		return weight;
	}

	
	
}
