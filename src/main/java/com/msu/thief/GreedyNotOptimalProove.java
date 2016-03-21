package com.msu.thief;

import com.msu.thief.algorithms.impl.exhaustive.ThiefExhaustive;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.SingleObjectiveThiefProblem;

public class GreedyNotOptimalProove {

	
	
	public static void main(String[] args) {
		
		SymmetricMap m = new SymmetricMap(3);
		m.set(0, 1, 10);
		m.set(0, 2, 10);
		m.set(1, 2, 10);
		
		ItemCollection<Item> items = new ItemCollection<>();
		items.add(0, new Item(365, 10));
		items.add(1, new Item(100, 5));
		items.add(2, new Item(100, 5));
		
		SingleObjectiveThiefProblem thief = new SingleObjectiveThiefProblem(m, items, 10, 1);
		
		ThiefExhaustive.run(thief);
		
		
	}
	
}
