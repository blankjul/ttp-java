package com.moo.ttp.profit;

import java.util.HashMap;
import java.util.List;

import com.moo.ttp.model.item.Item;

/**
 * The ProfitCalculator provides an interface for calculating the profit on a given map.
 */
public interface ProfitCalculator {
	
	/**
	 * Calculate the profit according to a given principle (linear, exponential, allow negatives values...)
	 * @param items
	 * @param pickingTimes
	 * @return
	 */
	public <T extends Item>  double run(List<T> items, HashMap<Integer, Double> pickingTimes);

	
}
