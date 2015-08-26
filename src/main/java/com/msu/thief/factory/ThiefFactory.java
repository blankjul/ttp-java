package com.msu.thief.factory;

import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.TravellingThiefProblem;

/**
 * This factory creates a map and items that could be collected at each city.
 *
 */
public class ThiefFactory {

	public static int MAP_BOUNDS = 1000;
	public static int ITEM_BOUNDS = 1000;
	
	public static TravellingThiefProblem create(int numOfCities, int itemsPerCity,  ItemFactory.TYPE type, double maxWeightPerc, String name) {
		TravellingThiefProblem s = create(numOfCities, itemsPerCity, type, maxWeightPerc);
		s.setName(name);
		return s;
	}

	public static TravellingThiefProblem create(int numOfCities, int itemsPerCity,  ItemFactory.TYPE type, double maxWeightPerc) {
		TravellingThiefProblem problem = new TravellingThiefProblem();
		problem.setMap(MapFactory.create(numOfCities, MAP_BOUNDS));

		ItemCollection<Item> items = new ItemCollection<Item>();
		
		long sumWeights = 0;
		for (int i = 0; i < numOfCities; i++) {
			for (int j = 0; j < itemsPerCity; j++) {
				
				double maxTime = (problem.getMap().getMax() * problem.getMap().getSize()) / problem.MIN_SPEED;
				
				Item item = ItemFactory.create(type, ITEM_BOUNDS, maxTime);
				sumWeights += item.getWeight();		
				items.add(i, item);
			}
		}
		
		problem.setItems(items);
		problem.setMaxWeight((int) (sumWeights * maxWeightPerc));
		return problem;
	}

}
