package com.msu.problems;

import java.util.List;
import java.util.function.Function;

import com.msu.moo.model.AProblem;
import com.msu.moo.util.exceptions.EvaluationException;
import com.msu.thief.model.Item;
import com.msu.thief.variable.pack.PackingList;

/**
 * This class represents the knapsack problem.
 * 
 * The Problem is defined by items that could be added and a maximal weight that
 * fits into the knapsack.
 *
 */
public class KnapsackProblem  extends AProblem<PackingList<?>> implements IPackingProblem{

	// ! maximal weight of the knapsack
	protected int maxWeight;

	// ! all items that could be added to the knapsack
	protected List<Item> items;
	

	/**
	 * Create a Knapsack Problem with predefined items and a maximal weight!
	 * 
	 * @param maxWeight
	 *            maximal weight of the knapsack
	 * @param items
	 *            that could be added
	 */
	public KnapsackProblem(int maxWeight, List<Item> items) {
		setMaxWeight(maxWeight);
		setItems(items);
	}


	/**
	 * @return sum of weights for given packing list
	 */
	public static <T extends Item> double getWeight(List<Item> items, List<Boolean> b) {
		return getSumItemAttribute(items, b, i -> i.getWeight());
	}
	

	/**
	 * @return sum of profits for given packing list
	 */
	public static <T extends Item> double getProfit(List<T> items, List<Boolean> b) {
		return getSumItemAttribute(items, b, i -> i.getProfit());
	}

	/**
	 * Calculate the sum by using a lambda expression
	 * 
	 * @param items
	 *            that could be added
	 * @param b
	 *            for the knapsack
	 * @param func
	 *            lambda expression
	 * @return resulting weight
	 */
	public static <T extends Item> double getSumItemAttribute(List<T> items, List<Boolean> b, Function<T, Double> func) {
		double value = 0;
		for (int j = 0; j < b.size(); j++) {
			if (b.get(j)) value += func.apply(items.get(j));
		}
		return value;
	}

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

	@Override
	protected void evaluate_(PackingList<?> var, List<Double> objectives, List<Double> constraintViolations) {
		List<Boolean> b = var.get();
		if (b.size() != items.size())
			throw new EvaluationException(String.format("Sizes of the varialbes are different %s != %s", b.size(),  items.size()));
		double weight = getWeight(items, b);
		if (weight > maxWeight) {
			objectives.add(0.0);
		} else {
			objectives.add(getProfit(items, b));
		}
	}
	
	public int numOfItems() {
		return items.size();
	}

	public int getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(int maxWeight) {
		if (maxWeight < 0) throw new RuntimeException("Maximal weight of knapsack must be positive!");
		this.maxWeight = maxWeight;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}


	

}
