package com.msu.knp.scenarios.impl;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.model.Item;
import com.msu.knp.model.PackingList;
import com.msu.moo.util.Pair;
import com.msu.moo.util.Random;
import com.msu.thief.scenarios.AScenario;

public class RandomKnapsackScenario extends AScenario<Pair<List<Item>, Integer>, PackingList<?>> {

	// ! this defines how the items are created dependent on the correlation.
	public static enum CORRELATION_TYPE {
		UNCORRELATED, WEAKLY_CORRELATED, STRONGLY_CORRELATED
	};

	// ! default maximal values for the weight and profit
	protected int maximalValue = 1000;

	// ! default maximal values for the weight and profit
	protected int numOfItems = -1;

	// ! default correlation type is uncorrelated
	protected CORRELATION_TYPE corrType = CORRELATION_TYPE.UNCORRELATED;

	// ! default max weight of knapsack regarding to all items
	protected double maxWeightPerc = 0.5;

	public RandomKnapsackScenario() {
		super();
	}

	
	public RandomKnapsackScenario(int numOfItems, double fillingPercentage, CORRELATION_TYPE corrType) {
		super();
		this.numOfItems = numOfItems;
		this.corrType = corrType;
		this.maxWeightPerc = fillingPercentage;
	}

	/**
	 * Create one item according to the properties which are set.
	 */
	public static Item create(CORRELATION_TYPE corrType, int maximalValue) {

		Random rnd = Random.getInstance();

		// fix the weight value
		int weight = rnd.nextInt(1, maximalValue);
		int profit = 0;

		// calculate the profit value
		switch (corrType) {
		case UNCORRELATED:
			profit = rnd.nextInt(1, maximalValue);
			break;
		case WEAKLY_CORRELATED:
			int epsW = (int) ((maximalValue * 0.05 == 0) ? 1 : maximalValue * 0.05);
			profit = rnd.nextInt(weight - epsW, weight + epsW);
			break;
		case STRONGLY_CORRELATED:
			int epsS = (int) ((maximalValue * 0.005 == 0) ? 1 : maximalValue * 0.005);
			profit = rnd.nextInt(weight - epsS, weight + epsS);
			break;

		}

		// stay in between the boundaries.
		if (profit < 0)
			profit = 0;
		if (profit > maximalValue)
			profit = maximalValue;

		Item i = new Item(profit, weight);
		return i;

	}
	
	public static Item create(CORRELATION_TYPE corrType) {
		return create(corrType, 1000);
	}
	

	public int getMaximalValue() {
		return maximalValue;
	}

	public void setMaximalValue(int maximalValue) {
		this.maximalValue = maximalValue;
	}

	public CORRELATION_TYPE getCorrType() {
		return corrType;
	}

	public void setCorrType(CORRELATION_TYPE corrType) {
		this.corrType = corrType;
	}

	@Override
	public Pair<List<Item>, Integer> getObject() {

		if (numOfItems == -1)
			throw new RuntimeException("Please specify numOfItems before using getObject().");

		long sumWeight = 0;

		List<Item> items = new ArrayList<>();
		for (int i = 0; i < numOfItems; i++) {
			Item item = create(corrType, maximalValue);
			items.add(item);
			sumWeight += item.getWeight();
		}

		int maxWeight = (int) (sumWeight * maxWeightPerc);

		return Pair.create(items, maxWeight);
	}

	
	@Override
	public PackingList<?> getOptimal() {
		return null;
	}

}
