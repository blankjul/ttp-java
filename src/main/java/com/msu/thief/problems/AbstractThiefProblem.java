package com.msu.thief.problems;

import java.util.List;

import org.apache.log4j.Logger;

import com.msu.moo.model.ASingleObjectiveProblem;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.evaluator.profit.ProfitEvaluator;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.evaluator.time.TimeEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.variable.TTPVariable;

public abstract class AbstractThiefProblem extends ASingleObjectiveProblem<TTPVariable> {
	
	static final Logger logger = Logger.getLogger(AbstractThiefProblem.class);
	
	// ! minimal speed of the salesman
	protected double minSpeed = 0.1d;

	// ! maximal speed of the salesman
	protected double maxSpeed = 1.0d;

	// evaluator objects
	protected ProfitEvaluator evalProfit = new NoDroppingEvaluator();
	protected TimeEvaluator evalTime = new StandardTimeEvaluator();

	// ! map where the salesman could visit cities
	protected SymmetricMap map = null;

	// ! maximal weight of the knapsack
	protected int maxWeight;

	// ! items and hash for storing the items and the mapping to the cities!
	protected ItemCollection<Item> items;
	

	public AbstractThiefProblem() {
	}

	public AbstractThiefProblem(SymmetricMap map, ItemCollection<Item> items, int maxWeight) {
		super();
		this.map = map;
		this.maxWeight = maxWeight;
		this.items = items;
	}
	
	

	public AbstractThiefProblem(String name, double minSpeed, double maxSpeed, ProfitEvaluator evalProfit, TimeEvaluator evalTime,
			SymmetricMap map, int maxWeight, ItemCollection<Item> items) {
		this.name = name;
		this.minSpeed = minSpeed;
		this.maxSpeed = maxSpeed;
		this.evalProfit = evalProfit;
		this.evalTime = evalTime;
		this.map = map;
		this.maxWeight = maxWeight;
		this.items = items;
	}
	

	public int numOfCities() {
		return map.getSize();
	}

	public int numOfItems() {
		return items.size();
	}

	public SymmetricMap getMap() {
		return map;
	}

	public void setMap(SymmetricMap map) {
		this.map = map;
	}

	public int getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	public ItemCollection<Item> getItemCollection() {
		return items;
	}

	public void setItems(ItemCollection<Item> items) {
		this.items = items;
	}

	public ProfitEvaluator getProfitEvaluator() {
		return evalProfit;
	}

	public void setProfitEvaluator(ProfitEvaluator evalProfit) {
		this.evalProfit = evalProfit;
	}

	public TimeEvaluator getTimeEvaluator() {
		return evalTime;
	}

	public void setTimeEvaluator(TimeEvaluator evalTime) {
		this.evalTime = evalTime;
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

	public List<Item> getItems() {
		return items.asList();
	}
	
	public Item getItem(int idx) {
		return items.asList().get(idx);
	}


	
	
}
