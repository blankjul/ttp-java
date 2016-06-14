package com.msu.thief.problem;


import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.MultiObjectiveThiefProblem;

public class PublicationScenario {

	public static MultiObjectiveThiefProblem getExampleMutliObjective() {
		
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
		
		MultiObjectiveThiefProblem problem = new MultiObjectiveThiefProblem(m, items, 3);
		problem.setProfitEvaluator(new ExponentialProfitEvaluator(0.9, 10));
		return problem;
	}
	
	
	public static SingleObjectiveThiefProblem getExampleSingleObjective() {
		
		SymmetricMap m = new SymmetricMap(4)
				.set(0, 1, 5)
				.set(0, 2, 6)
				.set(0, 3, 6)
				.set(1, 2, 5)
				.set(1, 3, 6)
				.set(2, 3, 4);
		
		ItemCollection<Item> items = new ItemCollection<Item>();
		items.add(2, new Item(100, 3));
		items.add(2, new Item(40, 1));
		items.add(2, new Item(40, 1));
		items.add(1, new Item(20, 2));
		items.add(2, new Item(30, 3));
		items.add(3, new Item(20, 2));
		
		SingleObjectiveThiefProblem problem = new SingleObjectiveThiefProblem(m, items, 3);
		problem.setProfitEvaluator(new NoDroppingEvaluator());
		problem.setName("PublicationScenario");
		return problem;
		
	}
	
	

}
