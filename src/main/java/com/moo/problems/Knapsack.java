package com.moo.problems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.moo.ttp.model.item.Item;

/**
 * This class represents the knapsack problem.
 * 
 * The Problem is defined by items that could be added and a maximal weight that
 * fits into the knapsack.
 *
 */
public class Knapsack implements Problem<Boolean[], Integer> {

	// ! maximal weight of the knapsack
	private int maxWeight;

	// ! all items that could be added to the knapsack
	private ArrayList<Item> items;

	/**
	 * Create a Knapsack Problem with predefined items and a maximal weight!
	 * 
	 * @param maxWeight
	 *            maximal weight of the knapsack
	 * @param items
	 *            that could be added
	 */
	public Knapsack(int maxWeight, ArrayList<Item> items) {
		super();
		this.maxWeight = maxWeight;
		this.items = items;
	}

	/**
	 * Evaluate the knapsack problem.
	 * 
	 * @param b
	 *            that defines which items to pick
	 * @return the profit of the knapsack
	 */
	public Integer evaluate(Boolean[] b) {
		if (b.length != items.size())
			throw new RuntimeException("Sizes of the varialbes are different " + b.length + " != " + items.size());
		int weight = getWeight(items, b);
		if (weight > maxWeight)
			return 0;
		else
			return getProfit(items, b);
	}

	public static <T extends Item> Integer getWeight(List<T> items, Boolean[] b) {
		return getSumItemAttribute(items, b, i -> i.getWeight());
	}

	public static <T extends Item> Integer getProfit(List<T> items, Boolean[] b) {
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
	public static <T extends Item> Integer getSumItemAttribute(List<T> items, Boolean[] b, Function<T, Integer> func) {
		int weight = 0;
		for (int j = 0; j < b.length; j++) {
			if (b[j]) weight += func.apply(items.get(j));
		}
		return weight;
	}

}
