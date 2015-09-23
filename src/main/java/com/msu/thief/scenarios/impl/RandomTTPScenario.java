package com.msu.thief.scenarios.impl;

import com.msu.knp.model.Item;
import com.msu.knp.scenarios.impl.RandomKnapsackScenario;
import com.msu.knp.scenarios.impl.RandomKnapsackScenario.CORRELATION_TYPE;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.evaluator.profit.IndividualProfitEvaluator;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.scenarios.AScenario;
import com.msu.tsp.scenarios.impl.RandomTSPScenario;

/**
 * This class represents a thief factory which allows to create a
 * TravellingThief Problem.
 */

public class RandomTTPScenario extends AScenario<TravellingThiefProblem, Object> {

	// ! default maximal values
	final public static int MAX_MAP_VALUES = 1000;

	// ! number of cities
	protected int numOfCities;

	// ! number of items per city
	protected int numOfItemsPerCity;

	// ! percentage of the maximal knapsack
	protected Double maxWeightPerc = null;

	// ! default correlation type is uncorrelated
	protected CORRELATION_TYPE corrType = CORRELATION_TYPE.UNCORRELATED;


	public RandomTTPScenario(int numOfCities, int numOfItemsPerCity, Double maxWeightPerc) {
		super();
		this.numOfCities = numOfCities;
		this.numOfItemsPerCity = numOfItemsPerCity;
		this.maxWeightPerc = maxWeightPerc;
	}

	public RandomTTPScenario(int numOfCities, int numOfItemsPerCity, Double maxWeightPerc, CORRELATION_TYPE corrType) {
		this(numOfCities, numOfItemsPerCity, maxWeightPerc);
		this.corrType = corrType;
	}

	@Override
	public TravellingThiefProblem getObject() {

		// create the map
		TravellingThiefProblem problem = new TravellingThiefProblem();
		problem.setMap(RandomTSPScenario.create(numOfCities));

		// create the items
		ItemCollection<Item> items = new ItemCollection<Item>();

		long sumWeights = 0;

		Double maximalTourTime = (problem.getMap().getMax() * problem.getMap().getSize()) / problem.getMinSpeed();

		for (int i = 0; i < numOfCities; i++) {
			for (int j = 0; j < numOfItemsPerCity; j++) {
				Item item = RandomKnapsackScenario.create(corrType);
				sumWeights += item.getWeight();
				double dropping = item.getProfit() / maximalTourTime;
				// double dropping = Random.getInstance().nextDouble(0.2, 0.8);
				items.add(i, new Item(item.getProfit(), item.getWeight(), dropping));
			}
		}

		problem.setItems(items);
		problem.setMaxWeight((int) (sumWeights * maxWeightPerc));
		problem.setProfitEvaluator(new IndividualProfitEvaluator());
		problem.setName(String.format("TTP-%s-%s-%s-%s", numOfCities, numOfItemsPerCity, maxWeightPerc, corrType));
		return problem;
	}

	public Double getMaxWeightPerc() {
		return maxWeightPerc;
	}

	public void setMaxWeightPerc(Double maxWeightPerc) {
		this.maxWeightPerc = maxWeightPerc;
	}

}
