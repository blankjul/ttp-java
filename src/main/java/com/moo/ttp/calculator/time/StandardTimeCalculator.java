package com.moo.ttp.calculator.time;

import java.util.HashMap;
import java.util.List;

import com.moo.ttp.TravellingThiefProblemSettings;
import com.moo.ttp.model.item.Item;
import com.moo.ttp.model.item.ItemCollection;
import com.moo.ttp.variable.TravellingThiefProblem;

public class StandardTimeCalculator implements TimeCalculator {


	
	protected double time = 0.d;
	
	protected double weight = 0.d;
	
	HashMap<Integer, Double> itemTimes;
	
	
	@Override
	public <T extends Item> void run(TravellingThiefProblem ttp, List<Integer> pi, List<Boolean> b) {
		
		TravellingThiefProblemSettings settings = ttp.getSettings();
		ItemCollection<Item> items = settings.getItems();

		time = 0;
		weight = 0;
		itemTimes = new HashMap<Integer, Double>();
		
		double speed = settings.getMaxSpeed();
		// iterate over all possible cities
		for (int i = 0; i < pi.size(); i++) {

			// for each item index this city
			for (Integer index : items.getItemsFromCityByIndex(pi.get(i))) {

				// if we pick this item
				if (b.get(index)) {

					// update the current weight
					weight += items.get(index).getWeight();

					double speedDiff = settings.getMaxSpeed() - settings.getMinSpeed();
					speed = settings.getMaxSpeed() - weight * speedDiff / settings.getMaxWeight();

					// if we are lower than minimum it is just the minimum
					// if this is the case the weight is larger than the
					// maxWeight!
					speed = Math.max(speed, settings.getMinSpeed());

					// save the picking time!
					if (itemTimes != null)
						itemTimes.put(index, time);

				}
			}

			// do not forget the way from the last city to the first!
			time += (settings.getMap().get(pi.get(i), pi.get((i + 1) % pi.size()) ) / speed);

		}
		
		// calculate the time of each item on the tour!
		for (Integer index : itemTimes.keySet()) {
			itemTimes.put(index, time - itemTimes.get(index));
		}
		


	}


	@Override
	public HashMap<Integer, Double> getItemTimes() {
		return itemTimes;
	}


	@Override
	public double getWeight() {
		return weight;
	}


	@Override
	public double getTime() {
		return time;
	}

}
