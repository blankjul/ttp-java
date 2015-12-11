package com.msu.thief.evolving;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;

import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractMutation;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.ThiefProblem;
import com.msu.util.MyRandom;

public class ThiefProblemMutation extends AbstractMutation<AbstractThiefProblem> {

	@Override
	protected AbstractThiefProblem mutate_(AbstractThiefProblem a,IProblem problem,  MyRandom rnd) {

		
		List<Point2D> cities = ((CoordinateMap) a.getMap()).getCities();
		for (int i = 0; i < a.numOfCities(); i++) {
			if (rnd.nextDouble() < 0.1) {
				cities.set(i, new Point2D.Double(rnd.nextDouble(0, 1000), rnd.nextDouble(0, 1000)));
			}
		}

		int maxWeight = a.getMaxWeight();
		if (rnd.nextDouble() < 0.5) {
			double tmp = a.getItemCollection().asList().stream().collect(Collectors.summingDouble(Item::getWeight));
			maxWeight = (int) (tmp *  rnd.nextDouble());
		}

		ItemCollection<Item> items = a.getItemCollection();
		ItemCollection<Item> mItems = new ItemCollection<>();
		for (int i = 0; i < a.numOfCities(); i++) {
			for (int j = 0; j < items.getItemsFromCity(i).size(); j++) {
				if (rnd.nextDouble() < 0.2) {
					mItems.add(i, new Item(rnd.nextInt(1, 1000), rnd.nextInt(1, 1000)));
				} else {
					Item item = items.getItemsFromCity(i).get(j);
					mItems.add(i, new Item(item.getProfit(), item.getWeight()));
				}
			}
		}
		
		return new ThiefProblem(new CoordinateMap(cities), mItems, maxWeight);
	}

}
