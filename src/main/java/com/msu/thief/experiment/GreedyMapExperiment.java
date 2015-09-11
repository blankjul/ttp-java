package com.msu.thief.experiment;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.algorithms.ExhaustiveSolver;
import com.msu.moo.experiment.OneProblemOneAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.ScatterPlot;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.factory.map.MapFactory.TYPE;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.TTPExhaustiveFactory;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.variable.TTPVariable;

public class GreedyMapExperiment extends OneProblemOneAlgorithmExperiment<TravellingThiefProblem> {

	@Override
	public void report() {
		ScatterPlot sp = new ScatterPlot("GreedyMap");
		for (NonDominatedSolutionSet set : expResult.get(problem, algorithm)) {
			sp.add(set.getSolutions(), "ExhaustiveResult");
		}
		sp.show();
	}

	@Override
	protected IAlgorithm<TravellingThiefProblem> getAlgorithm() {
		TTPExhaustiveFactory fac = new TTPExhaustiveFactory();
		ExhaustiveSolver<TTPVariable, TravellingThiefProblem> exhaustive = new ExhaustiveSolver<>(fac);
		return exhaustive;
	}

	@Override
	protected TravellingThiefProblem getProblem() {
		// create the map
		List<Point2D> cities = new ArrayList<>();
		cities.add(new Point2D.Double(0, 0));
		cities.add(new Point2D.Double(1, 1));
		cities.add(new Point2D.Double(3, 3));
		cities.add(new Point2D.Double(7, 7));
		cities.add(new Point2D.Double(15, 15));
		MapFactory fac = new MapFactory();
		fac.setType(TYPE.MANHATTEN_2D);
		com.msu.thief.model.Map m = fac.createFromDouble(cities);

		// add one random item to map
		ItemCollection<Item> items = new ItemCollection<>();
		// profit / weight!!!
		items.add(0, new Item(0, 1000));
		items.add(1, new Item(200, 800));
		items.add(2, new Item(400, 600));
		items.add(3, new Item(600, 400));
		items.add(4, new Item(800, 200));

		/*
		 * final int itemPerCity = 1; int counter = 0; for(Item item : new
		 * ItemFactory().create(cities.size() * itemPerCity)) {
		 * System.out.println(String.valueOf(counter) + " " + item);
		 * items.add(counter++, item); }
		 */

		// create problem
		TravellingThiefProblem ttp = new TravellingThiefProblem(m, items, 1000);
		ttp.setProfitEvaluator(new ExponentialProfitEvaluator(0.9, 10.0));

		return ttp;
	}

	@Override
	protected NonDominatedSolutionSet getTrueFront() {
		return null;
	}

}