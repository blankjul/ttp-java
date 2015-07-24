package com.moo.ttp;

import javax.management.JMException;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import com.moo.ttp.algorithms.MOEAD;
import com.moo.ttp.jmetal.jCrossover;
import com.moo.ttp.jmetal.jISolution;
import com.moo.ttp.jmetal.jMutation;
import com.moo.ttp.jmetal.jProblem;
import com.moo.ttp.model.Item;
import com.moo.ttp.model.Map;
import com.moo.ttp.problems.TravellingThiefProblem;


public class App 
{
	
	
	public static TravellingThiefProblem example() {
		Map m = new Map(4);
        m.set(0,1,5);
        m.set(0,2,6);
        m.set(0,3,6);
        m.set(1,2,5);
        m.set(1,3,6);
        m.set(2,3,4);
        TravellingThiefProblem ttp  = new TravellingThiefProblem(m, 3);
        ttp.addItem(2, new Item(10, 3));
        ttp.addItem(2, new Item(4, 1));
        ttp.addItem(2, new Item(4, 1));
        ttp.addItem(1, new Item(2, 2));
        ttp.addItem(2, new Item(3, 3));
        ttp.addItem(3, new Item(2, 2));
		return ttp;
	}
	
	public static void main(String[] args) throws JMException, jmetal.util.JMException {
		
		TravellingThiefProblem ttp = example();
        
		MOEAD moad = new MOEAD(new jProblem(ttp), 250000);
		moad.run();
		for (jISolution solution : moad.getResult()) {
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
