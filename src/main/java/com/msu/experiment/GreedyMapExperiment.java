package com.msu.experiment;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.msu.algorithms.AlgorithmFactory;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.evaluator.time.NotBackHomeTimeEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.scenarios.impl.RandomTSPScenario;
import com.msu.tsp.util.distances.ManhattenDistance;

public class GreedyMapExperiment extends AMultiObjectiveExperiment<TravellingThiefProblem> {

	final protected int NUM_OF_CITIES = 20;

	@Override
	protected void setAlgorithms(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		settings.addAlgorithm(AlgorithmFactory.createNSGAII());
	}

	@Override
	protected void setProblems(ExperimetSettings<TravellingThiefProblem, NonDominatedSolutionSet> settings) {
		// create the map
		List<Point2D> cities = new ArrayList<>();
		for (int i = 0; i < NUM_OF_CITIES; i++) {
			cities.add(new Point2D.Double(i, i));
		}

		SymmetricMap m = RandomTSPScenario.create(cities, new ManhattenDistance());

		// add one random item to map
		ItemCollection<Item> items = new ItemCollection<>();

		final int itemsPerCity = 1;
		int sumOfWeight = 1;
		for (int i = 1; i < cities.size(); i++) {
			for (int j = 0; j < itemsPerCity; j++) {
				Double value = Math.pow(2, i);
				Item item = new Item(10, value.intValue());
				items.add(i, item);
				sumOfWeight += item.getWeight();
			}
		}

		// create problem -> all items fit in the knapsack
		TravellingThiefProblem ttp = new TravellingThiefProblem(m, items, sumOfWeight);
		ttp.setProfitEvaluator(new ExponentialProfitEvaluator(0.9, 100.0));
		ttp.setTimeEvaluator(new NotBackHomeTimeEvaluator(ttp));

		settings.addProblem(ttp);

	}

}