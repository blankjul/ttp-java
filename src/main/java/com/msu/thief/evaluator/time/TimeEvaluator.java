package com.msu.thief.evaluator.time;

import java.util.HashMap;
import java.util.Map;

import com.msu.moo.util.Pair;
import com.msu.thief.evaluator.IEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.TravellingThiefProblem;

/**
 * The TimeCalculator provides an interface for calculating the time of a given
 * tour.
 */
public abstract class TimeEvaluator implements IEvaluator<Pair<Tour<?>, PackingList<?>>, Double> {

	// ! the problem instance
	protected TravellingThiefProblem problem;
	
	//! traveling time of the salesman
	protected Double time = null;

	//! final weight of the knapsack 
	protected Double weight = null;
	
	//! mapping of the items and the packing duration
	protected Map<Item, Double> mItem = new HashMap<>();

	public TimeEvaluator(TravellingThiefProblem problem) {
		super();
		this.problem = problem;
	}
	
	public Double getWeight() {
		return weight;
	}

	public Double getTime() {
		return time;
	}
	
	public Map<Item, Double> getItemMap() {
		return mItem;
	}

}
