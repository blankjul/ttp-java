package com.moo.ttp.problems.travellingthiefproblem;

import java.util.HashMap;
import java.util.List;

import com.moo.ttp.model.Item;

/**
 * The IndividualProfitCalculator calculates the profit by using a dropping rate for each item!
 */
public class IndividualProfitCalculator implements ProfitCalculator{

	@Override
	public <T extends Item> double calculate(List<T> items, HashMap<Integer, Double> pickingTimes) {
		return 0;
	}

	
	
}
