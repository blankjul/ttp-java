package com.moo.ttp.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.moo.ttp.model.Item;
import com.moo.ttp.model.Map;
import com.moo.ttp.util.Pair;

public class TravellingThiefProblem {

	private Map map;
	private int maxWeight;
	private ArrayList<Item> items;

	private HashMap<Integer, ArrayList<Integer>> hash;

	private double minSpeed = 0.1d;
	private double maxSpeed = 1.0d;
	private double droppingConstant = 10;
	private double droppingRate = 0.9;

	public TravellingThiefProblem(Map map) {
		this(map, -1);
	}

	public TravellingThiefProblem(Map map, int maxWeight) {
		super();
		this.map = map;
		this.maxWeight = maxWeight;
		this.items = new ArrayList<Item>();
		this.hash = new HashMap<Integer, ArrayList<Integer>>();
	}

	public void addItem(int city, Item i) {
		items.add(i);
		if (hash.containsKey(city)) {
			hash.get(city).add(items.size() - 1);
		} else {
			hash.put(city, new ArrayList<Integer>(Arrays.asList(items.size() - 1)));
		}
	}

	public int numOfCities() {
		return map.getSize();
	}

	public int numOfItems() {
		return items.size();
	}

	public Pair<Double, Double> evaluate(Integer[] pi, Boolean[] pickingPlan) {

		HashMap<Integer, Double> pickingTimes = new HashMap<Integer, Double>();

		double currentTime = 0;
		double currentSpeed = maxSpeed;
		double currentWeight = 0;

		// iterate over all possible cities
		for (int i = 0; i < pi.length; i++) {

			// look for all items at this city -> if there are one
			if (hash.containsKey(pi[i])) {
				for (Integer index : hash.get(pi[i])) {
					// if we pick the item change the weight
					if (pickingPlan[index]) {
						currentWeight += items.get(index).getWeight();
						currentSpeed = Math.max(minSpeed, maxSpeed - currentWeight * (maxSpeed - minSpeed) / maxWeight);
						pickingTimes.put(index, currentTime);
					}
				}
			}
			currentTime += (map.get(pi[i], pi[(i + 1) % pi.length]) / currentSpeed);
		}

		double currentProfit = 0d;
		if (currentWeight <= maxWeight) {
			for (Integer index : pickingTimes.keySet()) {
				currentProfit += items.get(index).getProfit() * Math.pow(droppingRate, (currentTime - pickingTimes.get(index)) / droppingConstant);
			}
		}
		return Pair.create(currentTime, currentProfit);

	}

	public int getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
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

	public double getDroppingConstant() {
		return droppingConstant;
	}

	public void setDroppingConstant(double droppingConstant) {
		this.droppingConstant = droppingConstant;
	}

	public double getDroppingRate() {
		return droppingRate;
	}

	public void setDroppingRate(double droppingRate) {
		this.droppingRate = droppingRate;
	}

}
