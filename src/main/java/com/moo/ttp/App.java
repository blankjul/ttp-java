package com.moo.ttp;

import javax.management.JMException;

import com.moo.ttp.algorithms.NSGAIII;
import com.moo.ttp.factory.ThiefFactory;
import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.jmetal.jProblem;
import com.moo.ttp.model.DroppingItem;
import com.moo.ttp.model.ItemCollection;
import com.moo.ttp.model.Map;
import com.moo.ttp.problems.TravellingThiefProblem;
import com.moo.ttp.problems.travellingthiefproblem.TravellingThiefProblemSettings;


public class App 
{
	
	
	public static TravellingThiefProblem example() {
		Map m = new Map(4).set(0,1,5).set(0,2,6).set(0,3,6).set(1,2,5).set(1,3,6).set(2,3,4);
        ItemCollection<DroppingItem> items = new ItemCollection<DroppingItem>();
        items.add(2, new DroppingItem(10, 3));
        items.add(2, new DroppingItem(4, 1));
        items.add(2, new DroppingItem(4, 1));
        items.add(1, new DroppingItem(2, 2));
        items.add(2, new DroppingItem(3, 3));
        items.add(3, new DroppingItem(2, 2));
        TravellingThiefProblemSettings s = new TravellingThiefProblemSettings(m, items, 3);
        s.setProfitCalculator("com.moo.ttp.problems.travellingthiefproblem.ExponentialProfitCalculator");
        return new TravellingThiefProblem(s);
	}
	
	public static void main(String[] args) throws JMException {
		
		TravellingThiefProblem ttp = ThiefFactory.create(20, 5);
        
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
