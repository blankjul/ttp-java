package com.msu.problems;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.msu.model.AProblem;
import com.msu.thief.evaluator.Evaluator;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.evaluator.profit.ProfitEvaluator;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.evaluator.time.TimeEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;
import com.msu.util.exceptions.EvaluationException;

public class ThiefProblem extends AProblem<TTPVariable> implements IPackingProblem, ICityProblem{
	
	static final Logger logger = Logger.getLogger(ThiefProblem.class);
	
	// ! minimal speed of the salesman
	protected double minSpeed = 0.1d;

	// ! maximal speed of the salesman
	protected double maxSpeed = 1.0d;

	// evaluator objects
	protected ProfitEvaluator evalProfit = new NoDroppingEvaluator();
	protected TimeEvaluator evalTime = new StandardTimeEvaluator(this);

	// ! map where the salesman could visit cities
	protected SymmetricMap map = null;

	// ! maximal weight of the knapsack
	protected int maxWeight;

	// ! items and hash for storing the items and the mapping to the cities!
	protected ItemCollection<Item> items;
	
	//! value for fixing the starting city at each evaluation
	protected boolean startingCityIsZero = true;

	
	public ThiefProblem() {
	}

	public ThiefProblem(SymmetricMap map, ItemCollection<Item> items, int maxWeight) {
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
	protected void evaluate_(TTPVariable var, List<Double> objectives, List<Double> constraintViolations) {
		
		// check for the correct input before using evaluator
		Pair<Tour<?>, PackingList<?>> pair = var.get();
		checkTour(pair.first);
		checkPackingList(pair.second);
		
		// fix the starting city if necessary
		if (startingCityIsZero) pair.first = rotateToCityZero(pair.first, true);
		
		// use the evaluators to calculate the result
		evalTime = new StandardTimeEvaluator(this);
		List<Double> result =  new Evaluator(this, evalProfit, evalTime).evaluate(var.get());
		for (Double d : result) objectives.add(d);
		
		if (evalTime.getWeight() <= getMaxWeight()) constraintViolations.add(0d);
		else  constraintViolations.add(evalTime.getWeight() - getMaxWeight()); 
		
		
	}
	
	public void checkTour(Tour<?> tour) {
		List<Integer> pi = tour.encode();
		SalesmanProblem.checkTourSize(map.getSize(), pi);
		SalesmanProblem.checkTourValidtiy(pi);
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
	public int getNumberOfConstraints() {
		return 1;
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

	@Override
	public List<Item> getItems() {
		return items.asList();
	}

	public boolean isStartingCityIsZero() {
		return startingCityIsZero;
	}

	public void setStartingCityIsZero(boolean fixStartingCitiy) {
		this.startingCityIsZero = fixStartingCitiy;
	}
	

	
	public static StandardTour rotateToCityZero(Tour<?> var, boolean log) {
		List<Integer> tour = var.encode();
		
		if (!tour.contains(0)) 
			throw new RuntimeException("Failed to start at city 0. It's not included at the tour!");
		int index = tour.indexOf(0);
		
		if (index != 0) {
			if (log) logger.info(String.format("Rotating city 0 to the first position: %s", tour));
			Collections.rotate(tour, -index);
		}
		return new StandardTour(tour);
	}
	

	
	
}
