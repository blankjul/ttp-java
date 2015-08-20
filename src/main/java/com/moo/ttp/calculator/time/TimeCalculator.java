package com.moo.ttp.time;

import java.util.HashMap;

import com.moo.ttp.TravellingThiefProblem;
import com.moo.ttp.model.item.Item;

/**
 * The TimeCalculator provides an interface for calculating the time of a given tour.
 */
public interface TimeCalculator {
	

	public <T extends Item> void run(TravellingThiefProblem ttp, Integer[] pi, Boolean[] b);

	public HashMap<Integer, Double> getItemTimes();
	
	public double getWeight();
	
	public double getTime();
	
	
}
