package com.msu.thief.problems;

import java.util.List;

import com.msu.moo.model.AbstractProblem;
import com.msu.thief.evaluator.Evaluator;
import com.msu.thief.evaluator.profit.IndividualProfitEvaluator;
import com.msu.thief.evaluator.profit.ProfitEvaluator;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.evaluator.time.TimeEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.Map;
import com.msu.thief.variable.TTPVariable;

public class TravellingThiefProblem extends AbstractProblem<TTPVariable> {

	// ! minimal speed of the salesman
	final public double MIN_SPEED = 0.1d;

	// ! maximal speed of the salesman
	final public double MAX_SPEED = 1.0d;

	// evaluator objects
	protected ProfitEvaluator evalProfit = new IndividualProfitEvaluator();
	protected TimeEvaluator evalTime = new StandardTimeEvaluator(this);

	// ! name of this problem instance
	protected String name = "NO_NAME";

	// ! map where the salesman could visit cities
	protected Map map = null;

	// ! maximal weight of the knapsack
	protected int maxWeight;

	// ! items and hash for storing the items and the mapping to the cities!
	protected ItemCollection<Item> items;

	
	public TravellingThiefProblem() {
	}

	public TravellingThiefProblem(Map map, ItemCollection<Item> items, int maxWeight) {
		super();
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

	@Override
	protected List<Double> evaluate_(TTPVariable variable) {
		return new Evaluator(this, evalProfit, evalTime).evaluate(variable.get());
	}

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

	@Override
	public String toString() {
		return name;
	}

	/*
	 * Getter and Setter Methods
	 */

	public void setName(String name) {
		this.name = name;
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

	public ItemCollection<Item> getItems() {
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

}
