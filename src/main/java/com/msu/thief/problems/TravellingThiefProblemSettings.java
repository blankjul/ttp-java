package com.msu.thief.problems;

import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.Map;

public class TravellingThiefProblemSettings {
	
	//! string for factoring a profit calculator
	protected String profitCalculator = "com.moo.ttp.calculator.profit.IndividualProfitCalculator";
	
	//! string for factoring a profit calculator
    protected String timeCalculator = "com.moo.ttp.calculator.time.StandardTimeCalculator";
		
	//! map where the salesman could visit cities
	protected Map map = null;
	
	//! maximal weight of the knapsack
	protected int maxWeight;
	
	//! minimal speed of the salesman
	protected double minSpeed = 0.1d;
	
	//! maximal speed of the salesman
	protected double maxSpeed = 1.0d;
	
	//! items and hash for storing the items and the mapping to the cities!
	protected ItemCollection<Item> items;
	
	
	public TravellingThiefProblemSettings() {
		super();
	}
	
	public TravellingThiefProblemSettings(Map map, ItemCollection<Item> items, int maxWeight) {
		super();
		this.map = map;
		this.items = items;
		this.maxWeight = maxWeight;
	}
	
	public String getProfitCalculator() {
		return profitCalculator;
	}
	public void setProfitCalculator(String profitCalculator) {
		this.profitCalculator = profitCalculator;
	}
	public String getTimeCalculator() {
		return timeCalculator;
	}
	public void setTimeCalculator(String timeCalculator) {
		this.timeCalculator = timeCalculator;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public int getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}
	public double getMinSpeed() {
		return minSpeed;
	}
	public void setMinSpeed(double minSpeed) {
		this.minSpeed = minSpeed;
	}
	public double getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public ItemCollection<Item> getItems() {
		return items;
	}
	public void setItems(ItemCollection<Item> items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		return String.format("%s-%s", map.getSize(), items.size() / map.getSize());
	}

	
	
	
	

}
