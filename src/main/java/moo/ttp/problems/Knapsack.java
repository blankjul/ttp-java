package moo.ttp.problems;

import java.util.ArrayList;
import moo.ttp.model.Item;

public class Knapsack {
	
	private int maxWeight;
	private ArrayList<Item> items;
	

	
	public Knapsack(int maxWeight, ArrayList<Item> items) {
		super();
		this.maxWeight = maxWeight;
		this.items = items;
	}



	public int evaluate(Boolean[] pickingPlan) {
		
		int sumWeight = 0;
		int sumProfit = 0;
		
		for (int j = 0; j < pickingPlan.length; j++) {
			if (pickingPlan[j]) {
				sumWeight += items.get(j).getWeight();
				sumProfit += items.get(j).getProfit();
				if (sumWeight > maxWeight) return 0;
			}
		}
		return sumProfit;
	}
	

}
