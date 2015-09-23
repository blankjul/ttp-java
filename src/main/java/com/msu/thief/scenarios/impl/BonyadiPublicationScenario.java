package com.msu.thief.scenarios.impl;

import com.msu.knp.model.Item;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.scenarios.AScenario;

public class BonyadiPublicationScenario extends AScenario<TravellingThiefProblem, Object>{

	@Override
	public TravellingThiefProblem getObject() {
		
		SymmetricMap m = new SymmetricMap(4)
				.set(0, 1, 5)
				.set(0, 2, 6)
				.set(0, 3, 6)
				.set(1, 2, 5)
				.set(1, 3, 6)
				.set(2, 3, 4);
		
		ItemCollection<Item> items = new ItemCollection<Item>();
		items.add(2, new Item(10, 3));
		items.add(2, new Item(4, 1));
		items.add(2, new Item(4, 1));
		items.add(1, new Item(2, 2));
		items.add(2, new Item(3, 3));
		items.add(3, new Item(2, 2));
		
		TravellingThiefProblem problem = new TravellingThiefProblem(m, items, 3);
		problem.setProfitEvaluator(new ExponentialProfitEvaluator());
		return problem;
		
	}


}
