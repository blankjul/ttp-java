package com.moo.ttp.problems.ttp.profit;

import java.util.HashMap;
import java.util.List;

import com.moo.ttp.model.item.Item;
/**
 * The IndividualProfitCalculator calculates the profit by using a dropping rate for each item!
 */
public class IndividualProfitCalculator implements ProfitCalculator{

	@Override
	public <T extends Item> double run (List<T> items, HashMap<Integer, Double> pickingTimes) {
		double profit = 0;
		for (Integer index : pickingTimes.keySet()) {
			Item d = (Item) items.get(index);
			double profitEndOfTour = d.getProfit() - d.getDropping() * pickingTimes.get(index);
			if (profitEndOfTour > 0)profit += profitEndOfTour;
		}
		return profit;
	}


}
