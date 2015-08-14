package com.moo.ttp;

import javax.management.JMException;

import com.moo.algorithms.NSGAIII;
import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.jmetal.jProblem;
import com.moo.ttp.model.Map;
import com.moo.ttp.model.item.Item;
import com.moo.ttp.model.item.ItemCollection;


public class App 
{
	
	
	public static TravellingThiefProblem example() {
		Map m = new Map(4).set(0,1,5).set(0,2,6).set(0,3,6).set(1,2,5).set(1,3,6).set(2,3,4);
        ItemCollection<Item> items = new ItemCollection<Item>();
        items.add(2, new Item(10, 3));
        items.add(2, new Item(4, 1));
        items.add(2, new Item(4, 1));
        items.add(1, new Item(2, 2));
        items.add(2, new Item(3, 3));
        items.add(3, new Item(2, 2));
        TravellingThiefProblemSettings s = new TravellingThiefProblemSettings(m, items, 3);
        s.setProfitCalculator("com.moo.ttp.profit.ExponentialProfitCalculator");
        return new TravellingThiefProblem(s);
	}
	
	public static void main(String[] args) throws JMException {
		
		TravellingThiefProblem ttp = example();
        
		NSGAIII a = new NSGAIII(new jProblem(ttp), 250000, 100, 12);
		a.run();
		for (jISolution solution : a.getResult()) {
			System.out.println(solution);
		}
		
		/*
		NSGAII<jISolution> nsga = new NSGAII<jISolution>(new jProblem(ttp), 25000, 100,
			      new jCrossover(), new jMutation(),
			      new BinaryTournamentSelection<jISolution>(), new SequentialSolutionListEvaluator<jISolution>());
		nsga.run();
		
		for (jISolution solution : nsga.getResult()) {
			System.out.println(solution);
		}
		*/


	}
}
