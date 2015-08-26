package com.msu.thief.evaluator.time;

import java.util.HashMap;
import java.util.List;

import com.msu.thief.model.Item;
import com.msu.thief.variable.TravellingThiefProblem;

/**
 * The TimeCalculator provides an interface for calculating the time of a given tour.
 */
public interface TimeCalculator {
	

	public <T extends Item> void run(TravellingThiefProblem ttp, List<Integer> pi, List<Boolean> b);

	public HashMap<Integer, Double> getItemTimes();
	
	public double getWeight();
	
	public double getTime();
	
	
}
