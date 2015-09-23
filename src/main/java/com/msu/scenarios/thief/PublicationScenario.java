package com.msu.scenarios.thief;

import com.msu.knp.model.Item;
import com.msu.scenarios.AThiefScenario;
import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;

public class PublicationScenario extends AThiefScenario<ThiefProblem, Object>{

	@Override
	public ThiefProblem getObject() {
		
		SymmetricMap m = new SymmetricMap(4);
		m.set(0, 1, 4);
		m.set(0, 2, 10);
		m.set(0, 3, 3);
		m.set(1, 2, 5);
		m.set(1, 3, 6);
		m.set(2, 3, 8);

		ItemCollection<Item> items = new ItemCollection<>();
		items.add(0, new Item(30, 25));
		items.add(1, new Item(34, 30));
		items.add(2, new Item(40, 40));
		items.add(3, new Item(25, 21));

		ThiefProblem ttp = new ThiefProblem(m, items, 80);
		ttp.setProfitEvaluator(new ExponentialProfitEvaluator(0.9, 10.0));

		return ttp;
	}



}
