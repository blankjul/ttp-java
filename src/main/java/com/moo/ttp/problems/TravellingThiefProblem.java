package com.moo.ttp.problems;

import java.util.HashMap;

import com.moo.ttp.model.DroppingItem;
import com.moo.ttp.model.ItemCollection;
import com.moo.ttp.problems.travellingthiefproblem.ProfitCalculator;
import com.moo.ttp.problems.travellingthiefproblem.ProfitCalculatorFactory;
import com.moo.ttp.problems.travellingthiefproblem.TravellingThiefProblemSettings;
import com.moo.ttp.util.Pair;

public class TravellingThiefProblem {

	TravellingThiefProblemSettings settings = null;

	public TravellingThiefProblem(TravellingThiefProblemSettings settings) {
		this.settings = settings;
	}

	public int numOfCities() {
		return settings.getMap().getSize();
	}

	public int numOfItems() {
		return settings.getItems().size();
	}
	
	@Override
	public String toString() {
		return settings.toString();
	}

	
	public static Pair<Double,Integer> calculateTime(TravellingThiefProblemSettings settings, Integer[] pi, Boolean[] b, 
			HashMap<Integer, Double> pickingTimes) {
		ItemCollection<DroppingItem> items = settings.getItems();
		
		double time = 0;
		double speed = settings.getMaxSpeed();
		int weight = 0;
		// iterate over all possible cities
		for (int i = 0; i < pi.length; i++) {
			
			// for each item index this city
			for (Integer index : items.getItemsFromCityByIndex(pi[i])) {
				
				// if we pick this item
				if (b[index]) {
					
					// update the current weight
					weight += items.get(index).getWeight();
					
					double speedDiff = settings.getMaxSpeed() - settings.getMinSpeed();
					speed = settings.getMaxSpeed() - weight * speedDiff / settings.getMaxWeight();
					
					// if we are lower than minimum it is just the minimum
					// if this is the case the weight is larger than the maxWeight!
					speed = Math.max(speed, settings.getMinSpeed());
					
					// save the picking time!
					if (pickingTimes != null) pickingTimes.put(index, time);
					
				}
			}
			
			// do not forget the way from the last city to the first!
			time += (settings.getMap().get(pi[i], pi[(i + 1) % pi.length]) / speed);
			
		}
		return Pair.create( time, weight);
	}
	
	

	public Pair<Double, Double> evaluate(Integer[] pi, Boolean[] b) {

		HashMap<Integer, Double> pickingTimes = new HashMap<Integer, Double>();
		
		// calculate the salesman time
		Pair<Double,Integer> res = calculateTime(settings, pi, b, pickingTimes);
		double time = res.first;
		double weight = res.second;

		// calculate the time of each item on the tour!
		for (Integer index : pickingTimes.keySet()) {
			pickingTimes.put(index, time - pickingTimes.get(index));
		}
		
		// create a profit calculator
		double profit = 0;
		ProfitCalculator pc = ProfitCalculatorFactory.create(settings.getProfitCalculator());
		if (weight <= settings.getMaxWeight()) profit = pc.calculate(settings.getItems().getItems(), pickingTimes);
		
		if (profit < 0)
			throw new RuntimeException("Profit has to be larger than 0! But it is " + profit);
		
		return Pair.create(time, profit);

	}

}
