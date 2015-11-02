package com.msu.problems.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.util.Random;
import com.msu.problems.KnapsackProblem;
import com.msu.thief.model.Item;

public class RandomKnapsackProblemFactory extends AKnapsackProblemFactory {

	
	// ! this defines how the items are created dependent on the correlation.
	public static enum CORRELATION_TYPE {
		UNCORRELATED, WEAKLY_CORRELATED, STRONGLY_CORRELATED
	};
	
	// ! default maximal values for the weight and profit
	protected int maximalValue = 1000;


	// ! default correlation type is uncorrelated
	protected CORRELATION_TYPE corrType = CORRELATION_TYPE.UNCORRELATED;



	@Override
	public KnapsackProblem create(int numOfItems, double maxWeightPerc, Random rand) {
		
		long sumWeight = 0;

		List<Item> items = new ArrayList<>();
		for (int i = 0; i < numOfItems; i++) {
			Item item = create(corrType, maximalValue, rand);
			items.add(item);
			sumWeight += item.getWeight();
		}

		int maxWeight = (int) (sumWeight * maxWeightPerc);

		return new KnapsackProblem(maxWeight, items);
	}


	/**
	 * Create one item according to the properties which are set.
	 */
	public static Item create(CORRELATION_TYPE corrType, int maximalValue, Random rnd) {


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
	

	public int getMaximalValue() {
		return maximalValue;
	}

	public RandomKnapsackProblemFactory setMaximalValue(int maximalValue) {
		this.maximalValue = maximalValue;
		return this;
	}

	public CORRELATION_TYPE getCorrType() {
		return corrType;
	}

	public RandomKnapsackProblemFactory setCorrType(CORRELATION_TYPE corrType) {
		this.corrType = corrType;
		return this;
	}



	
}
