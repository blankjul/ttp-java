package com.moo.ttp.factory;

import com.moo.ttp.model.Item;
import com.moo.ttp.model.ItemCollection;
import com.moo.ttp.problems.ttp.TravellingThiefProblem;
import com.moo.ttp.problems.ttp.TravellingThiefProblemSettings;

/**
 * This factory creates a map and items that could be collected at each city.
 *
 */
public class ThiefFactory {

	public static int MAP_BOUNDS = 1000;
	public static int ITEM_BOUNDS = 1000;

	public static TravellingThiefProblem create(int numOfCities, int itemsPerCity,  ItemFactory.TYPE type, double maxWeightPerc) {
		TravellingThiefProblemSettings s = new TravellingThiefProblemSettings();
		s.setMap(MapFactory.create(numOfCities, MAP_BOUNDS));

		ItemCollection<Item> items = new ItemCollection<Item>();
		
		long sumWeights = 0;
		for (int i = 0; i < numOfCities; i++) {
			for (int j = 0; j < itemsPerCity; j++) {
				
				double maxTime = (s.getMap().getMax() * s.getMap().getSize()) / s.getMinSpeed();
				
				Item item = ItemFactory.create(type, ITEM_BOUNDS, maxTime);
				sumWeights += item.getWeight();		
				items.add(i, item);
			}
		}
		
		s.setItems(items);
		s.setMaxWeight((int) (sumWeights * maxWeightPerc));
		return new TravellingThiefProblem(s);
	}

}
