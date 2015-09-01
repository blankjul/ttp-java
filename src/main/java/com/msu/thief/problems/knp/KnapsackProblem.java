package com.msu.thief.problems.knp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.msu.moo.model.AbstractProblem;
import com.msu.thief.model.Item;
import com.msu.thief.problems.IPackingProblem;

/**
 * This class represents the knapsack problem.
 * 
 * The Problem is defined by items that could be added and a maximal weight that
 * fits into the knapsack.
 *
 */
public class KnapsackProblem  extends AbstractProblem<KnapsackVariable> implements IPackingProblem{

	// ! maximal weight of the knapsack
	private int maxWeight;

	// ! all items that could be added to the knapsack
	private List<Item> items;

	/**
	 * Create a Knapsack Problem with predefined items and a maximal weight!
	 * 
	 * @param maxWeight
	 *            maximal weight of the knapsack
	 * @param items
	 *            that could be added
	 */
	public KnapsackProblem(int maxWeight, List<Item> items) {
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
	public Integer evaluate(List<Boolean> b) {
		if (b.size() != items.size())
			throw new RuntimeException("Sizes of the varialbes are different " + b.size() + " != " + items.size());
		int weight = getWeight(items, b);
		if (weight > maxWeight)
			return 0;
		else
			return getProfit(items, b);
	}

	public static <T extends Item> Integer getWeight(List<T> items, List<Boolean> b) {
		return getSumItemAttribute(items, b, i -> i.getWeight());
	}

	public static <T extends Item> Integer getProfit(List<T> items, List<Boolean> b) {
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
	public static <T extends Item> Integer getSumItemAttribute(List<T> items, List<Boolean> b, Function<T, Integer> func) {
		int weight = 0;
		for (int j = 0; j < b.size(); j++) {
			if (b.get(j)) weight += func.apply(items.get(j));
		}
		return weight;
	}

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

	@Override
	protected List<Double> evaluate_(KnapsackVariable variable) {
		double profit = evaluate(variable.get().encode());
		return new ArrayList<Double>(Arrays.asList(-profit));
	}
	
	public int numOfItems() {
		return items.size();
	}

}
