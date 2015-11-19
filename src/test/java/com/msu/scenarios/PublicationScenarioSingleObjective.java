package com.msu.scenarios;

import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblem;

public class PublicationScenarioSingleObjective extends AThiefScenario<SingleObjectiveThiefProblem, Object> {

	@Override
	public SingleObjectiveThiefProblem getObject() {
		
		ThiefProblem mo = new PublicationScenario().getObject();
		
		ItemCollection<Item> items = new ItemCollection<Item>();
		items.add(2, new Item(100, 3));
		items.add(2, new Item(40, 1));
		items.add(2, new Item(40, 1));
		items.add(1, new Item(20, 2));
		items.add(2, new Item(30, 3));
		items.add(3, new Item(20, 2));
		
		SingleObjectiveThiefProblem problem = new SingleObjectiveThiefProblem(mo.getMap(), items, mo.getMaxWeight(), 1);
		
		return problem;
		
	}


}
