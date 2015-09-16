package com.msu.experiment;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.experiment.OneProblemOneAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.AlgorithmFactory;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.evaluator.time.NotBackHomeTimeEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.scenarios.impl.RandomTSPScenario;
import com.msu.tsp.util.distances.ManhattenDistance;
import com.msu.visualize.ColoredTourScatterPlot;

public class GreedyMapExperiment extends OneProblemOneAlgorithmExperiment<TravellingThiefProblem> {

	
	final protected int NUM_OF_CITIES = 50;
	
	@Override
	public void report() {
		for(NonDominatedSolutionSet set : expResult.get(problem, algorithm)) {
			ColoredTourScatterPlot sp = new ColoredTourScatterPlot("GreedyMap");
			sp.add(set.getSolutions());
			System.out.println(set);
			sp.show();
		}
	}

	@Override
	protected IAlgorithm<TravellingThiefProblem> getAlgorithm() {
		/*
		TTPExhaustiveFactory fac = new TTPExhaustiveFactory();
		ExhaustiveSolver<TTPVariable, TravellingThiefProblem> exhaustive = new ExhaustiveSolver<>(fac);
		return exhaustive;
		*/
		return AlgorithmFactory.createNSGAII();
	}

	@Override
	protected TravellingThiefProblem getProblem() {
		// create the map
		List<Point2D> cities = new ArrayList<>();
		for (int i = 0; i < NUM_OF_CITIES; i++) {
			cities.add(new Point2D.Double(i, i));
		}
		
		SymmetricMap m = RandomTSPScenario.create(cities, new ManhattenDistance(), null);

		// add one random item to map
		ItemCollection<Item> items = new ItemCollection<>();
		
		final int itemsPerCity = 1;
		final int weightPerItem = 10;
		
		// profit / weight!!!
		int sumOfWeight = 1;
		for (int i = 0; i < cities.size(); i++) {
			for (int j = 0; j < itemsPerCity; j++) {
				Double value = Math.pow(2, weightPerItem);
				Item item = new Item(value.intValue(), value.intValue());
				items.add(i, item);
				sumOfWeight += item.getWeight();
			}
		}


		// create problem -> all items fit in the knapsack
		TravellingThiefProblem ttp = new TravellingThiefProblem(m, items, sumOfWeight);
		ttp.setProfitEvaluator(new ExponentialProfitEvaluator(0.9, 10.0));
		ttp.setTimeEvaluator(new NotBackHomeTimeEvaluator(ttp));
		
		
		return ttp;
	}

	@Override
	protected NonDominatedSolutionSet getTrueFront() {
		return null;
	}

}