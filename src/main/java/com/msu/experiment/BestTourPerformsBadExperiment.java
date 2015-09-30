package com.msu.experiment;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.msu.knp.model.Item;
import com.msu.moo.interfaces.IProblem;
import com.msu.scenarios.tsp.RandomTSPScenario;
import com.msu.thief.ThiefProblem;
import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;

public class BestTourPerformsBadExperiment extends OneScenarioExperiment {

	@Override
	protected void setProblems(List<IProblem> problems) {

		SymmetricMap m = new SymmetricMap(6);
		/*
		 * for (int i = 0; i < numOfCities; i++) { for (int j = 0; j <
		 * numOfCities; j++) { m.set(i, j, Random.getInstance().nextDouble(99,
		 * 101)); } }
		 */

		ArrayList<Point2D> cities = new ArrayList<Point2D>();
		cities.add(new Point2D.Double(28.0, 104.0));
		cities.add(new Point2D.Double(894.0, 346.0));
		cities.add(new Point2D.Double(265.0, 883.0));
		cities.add(new Point2D.Double(210.0, 682.0));
		cities.add(new Point2D.Double(254.0, 303.0));
		cities.add(new Point2D.Double(748.0, 406.0));
		m = RandomTSPScenario.create(cities);

		ItemCollection<Item> items = new ItemCollection<>();
		items.add(0, new Item(20, 19));
		items.add(1, new Item(37, 37));
		items.add(2, new Item(99, 99));
		items.add(3, new Item(82, 82));
		items.add(4, new Item(92, 92));
		items.add(5, new Item(100, 99));

		ThiefProblem problem = new ThiefProblem(m, items, 394);

		problem.setProfitEvaluator(new NoDroppingEvaluator());
		problem.setStartingCityIsZero(STARTING_CITY_IS_ZERO);
		problems.add(problem);
	}

}
