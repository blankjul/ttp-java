package com.moo.ttp.problems.ttp.time;

import java.util.HashMap;

import com.moo.ttp.model.Item;
import com.moo.ttp.problems.ttp.TravellingThiefProblem;

/**
 * The TimeCalculator provides an interface for calculating the time of a given tour.
 */
public interface TimeCalculator {
	

	public <T extends Item> void run(TravellingThiefProblem ttp, Integer[] pi, Boolean[] b);

	public HashMap<Integer, Double> getItemTimes();
	
	public double getWeight();
	
	public double getTime();
	
	
}
