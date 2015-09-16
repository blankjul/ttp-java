package com.msu.thief;

import java.util.List;

import com.msu.knp.IPackingProblem;
import com.msu.moo.exception.EvaluationException;
import com.msu.moo.model.AbstractProblem;
import com.msu.moo.util.Pair;
import com.msu.thief.evaluator.Evaluator;
import com.msu.thief.evaluator.profit.IndividualProfitEvaluator;
import com.msu.thief.evaluator.profit.ProfitEvaluator;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.evaluator.time.TimeEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.TravellingSalesmanProblem;

public class TravellingThiefProblem extends AbstractProblem<TTPVariable> implements IPackingProblem, ICityProblem{
	
	// ! minimal speed of the salesman
	protected double minSpeed = 0.1d;

	// ! maximal speed of the salesman
	protected double maxSpeed = 1.0d;

	// evaluator objects
	protected ProfitEvaluator evalProfit = new IndividualProfitEvaluator();
	protected TimeEvaluator evalTime = new StandardTimeEvaluator(this);

	// ! name of this problem instance
	protected String name = "NO_NAME";

	// ! map where the salesman could visit cities
	protected SymmetricMap map = null;

	// ! maximal weight of the knapsack
	protected int maxWeight;

	// ! items and hash for storing the items and the mapping to the cities!
	protected ItemCollection<Item> items;

	
	public TravellingThiefProblem() {
	}

	public TravellingThiefProblem(SymmetricMap map, ItemCollection<Item> items, int maxWeight) {
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
		
		// check for the correct input before using evaluator
		Pair<Tour<?>, PackingList<?>> pair = variable.get();
		checkTour(pair.first);
		checkPackingList(pair.second);
		
		// use the evaluators to calculate the result
		return new Evaluator(this, evalProfit, evalTime).evaluate(variable.get());
	}
	
	public void checkTour(Tour<?> tour) {
		List<Integer> pi = tour.encode();
		TravellingSalesmanProblem.checkTourSize(map.getSize(), pi);
		TravellingSalesmanProblem.checkTourValidtiy(pi);
	}
	
    public void checkPackingList(PackingList<?> list) {
    	final int length = list.encode().size();
		if (length != numOfItems()) 
			throw new EvaluationException(String.format("Probem has %s items but picking vector only %s", numOfItems(), length));
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

}