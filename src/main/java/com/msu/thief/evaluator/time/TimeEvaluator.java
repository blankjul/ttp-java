package com.msu.thief.evaluator.time;

import java.util.HashMap;
import java.util.Map;

import com.msu.thief.evaluator.IEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;

/**
 * The TimeCalculator provides an interface for calculating the time of a given
 * tour.
 */
public abstract class TimeEvaluator implements IEvaluator<Pair<Tour<?>, PackingList<?>>, Double> {

	// ! the problem instance
	protected ThiefProblem problem;
	
	//! traveling time of the salesman
	protected Double time = null;

	//! final weight of the knapsack 
	protected Double weight = null;
	
	//! mapping of the items and the packing duration
	protected Map<Item, Double> mItem = new HashMap<>();

	public TimeEvaluator(ThiefProblem problem) {
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

	@Override
	public Double evaluate(Pair<Tour<?>, PackingList<?>> input) {
		this.time = 0.0d;
		this.weight = 0.0d;
		mItem.clear();
		return evaluate_(input);
	}
	
	protected abstract Double evaluate_(Pair<Tour<?>, PackingList<?>> input) ;
	

}
