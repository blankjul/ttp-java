package com.moo.ttp.experiment.factory;

import com.moo.ttp.TravellingThiefProblem;
import com.moo.ttp.TravellingThiefProblemSettings;
import com.moo.ttp.model.item.Item;
import com.moo.ttp.model.item.ItemCollection;

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
