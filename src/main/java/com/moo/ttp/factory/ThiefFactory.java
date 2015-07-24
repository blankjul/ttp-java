package com.moo.ttp.factory;

import com.moo.ttp.model.DroppingItem;
import com.moo.ttp.model.Item;
import com.moo.ttp.model.ItemCollection;
import com.moo.ttp.problems.TravellingThiefProblem;
import com.moo.ttp.problems.travellingthiefproblem.TravellingThiefProblemSettings;

public class ThiefFactory {

	public static int MAP_BOUNDS = 1000;
	public static int ITEM_BOUNDS = 1000;
	public static double MAX_WEIGHT_PERC = 0.6;
	public static ItemFactory.TYPE ITEM_TYPE = ItemFactory.TYPE.WEAKLY_CORRELATED;

	public static TravellingThiefProblem create(int numOfCities, int itemsPerCity) {
		TravellingThiefProblemSettings s = new TravellingThiefProblemSettings();
		s.setMap(MapFactory.create(numOfCities, MAP_BOUNDS));

		ItemCollection<DroppingItem> items = new ItemCollection<DroppingItem>();
		
		long sumWeights = 0;
		for (int i = 0; i < numOfCities; i++) {
			for (int j = 0; j < itemsPerCity; j++) {
				Item tmp = ItemFactory.create(ITEM_TYPE, ITEM_BOUNDS);
				DroppingItem item = new DroppingItem(tmp.getProfit(), tmp.getWeight());
				
				// calculate the estimation of the maximal tour
				double maxTime = (s.getMap().getMax() * s.getMap().getSize()) / s.getMinSpeed();
				item.setDropping(item.getProfit() / maxTime);
				
				sumWeights += item.getWeight();		
				items.add(i, item);
			}
		}
		
		s.setItems(items);
		s.setMaxWeight((int) (sumWeights * MAX_WEIGHT_PERC));
		return new TravellingThiefProblem(s);
	}

}
