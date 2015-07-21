package com.moo.ttp.factory;

import com.moo.ttp.model.Item;
import com.moo.ttp.problems.TravellingThiefProblem;

public class ThiefFactory {
	
	public static int MAP_BOUNDS = 1000;
	public static int ITEM_BOUNDS = 1000;
	public static double MAX_WEIGHT_PERC = 0.2;
	public static ItemFactory.TYPE ITEM_TYPE = ItemFactory.TYPE.STRONGLY_CORRELATED;
	
	
	public static TravellingThiefProblem create(int numOfCities, int itemsPerCity) {
		TravellingThiefProblem ttp = new TravellingThiefProblem(MapFactory.create(numOfCities, MAP_BOUNDS));
		long sumWeights = 0;
		for (int i = 0; i < numOfCities; i++) {
			for (int j = 0; j < itemsPerCity; j++) {
				Item item = ItemFactory.create(ITEM_TYPE, ITEM_BOUNDS);
				sumWeights += item.getWeight();		
				ttp.addItem(i, item);
			}
		}
		ttp.setMaxWeight((int) (sumWeights * MAX_WEIGHT_PERC));
		return ttp;
	}
	
	
	

}
