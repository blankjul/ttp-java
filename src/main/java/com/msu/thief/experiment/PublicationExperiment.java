package com.msu.thief.experiment;

import java.util.Arrays;

import com.msu.moo.algorithms.ExhaustiveSolver;
import com.msu.moo.experiment.MultiObjectiveExperiment;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.Map;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.TTPExhaustiveFactory;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.variable.TTPVariable;

public class PublicationExperiment extends MultiObjectiveExperiment<TravellingThiefProblem>  {

	
	@Override
	protected void setAlgorithms() {

		TTPExhaustiveFactory fac = new TTPExhaustiveFactory();
		ExhaustiveSolver<TTPVariable, TravellingThiefProblem> exhaustive = new ExhaustiveSolver<>(fac);
		algorithms.add(exhaustive);
	}

	
	
	@Override
	protected void setProblem() {
		Map m = new Map(4);
		m.set(0, 1, 4);
		m.set(0, 2, 10);
		m.set(0, 3, 3);
		m.set(1, 2, 5);
		m.set(1, 3, 6);
		m.set(2, 3, 8);
		
	
		ItemCollection<Item> items = new ItemCollection<>();
		items.add(0, new Item(30,25));
		items.add(1, new Item(34,30));
		items.add(2, new Item(40,40));
		items.add(3, new Item(25,21));
		
		// create problem
		TravellingThiefProblem ttp = new TravellingThiefProblem(m, items, 80);
		ttp.setProfitEvaluator(new ExponentialProfitEvaluator(0.9, 10.0));
		
		// execute hand calculations
		Tour<?> tour = new StandardTour(Arrays.asList(2,1,0,3));
		PackingList<?> list = new BooleanPackingList(Arrays.asList(false, true, true, false));
		Solution result = ttp.evaluate(new TTPVariable(Pair.create(tour, list)));
		System.out.println(result);
		
		// set problem
		this.problem = ttp;
		
	}
	
	

}