package com.moo.ttp.calculator.time;

import java.util.HashMap;
import java.util.List;

import com.moo.ttp.model.item.Item;
import com.moo.ttp.variable.TravellingThiefProblem;

/**
 * The TimeCalculator provides an interface for calculating the time of a given tour.
 */
public interface TimeCalculator {
	

	public <T extends Item> void run(TravellingThiefProblem ttp, List<Integer> pi, List<Boolean> b);

	public HashMap<Integer, Double> getItemTimes();
	
	public double getWeight();
	
	public double getTime();
	
	
}
