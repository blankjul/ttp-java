package com.msu.experiment.proofs;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.msu.NSGAIIFactory;
import com.msu.knp.model.Item;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.evaluator.time.NotBackHomeTimeEvaluator;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.util.distances.ManhattenDistance;

public class GreedyMapExperiment extends AExperiment {

	final protected int NUM_OF_CITIES = 20;

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(NSGAIIFactory.createNSGAII());
	}

	@Override
	protected void setProblems(List<IProblem> problems) {
		// create the map
		List<Point2D> cities = new ArrayList<>();
		for (int i = 0; i < NUM_OF_CITIES; i++) {
			cities.add(new Point2D.Double(i, i));
		}

		SymmetricMap m = new CoordinateMap(cities, new ManhattenDistance());

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
		ThiefProblem ttp = new ThiefProblem(m, items, sumOfWeight);
		ttp.setProfitEvaluator(new ExponentialProfitEvaluator(0.9, 100.0));
		ttp.setTimeEvaluator(new NotBackHomeTimeEvaluator(ttp));

		problems.add(ttp);

	}

}