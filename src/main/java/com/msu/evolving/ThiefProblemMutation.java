package com.msu.evolving;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;

import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.util.Random;
import com.msu.problems.ThiefProblem;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;

public class ThiefProblemMutation extends AbstractMutation<ThiefProblem> {

	@Override
	protected void mutate_(ThiefProblem a) {
		Random rnd = Random.getInstance();

		List<Point2D> cities = ((CoordinateMap) a.getMap()).getCities();
		for (int i = 0; i < a.numOfCities(); i++) {
			if (rnd.nextDouble() < 0.1) {
				cities.set(i, new Point2D.Double(rnd.nextDouble(0, 1000), rnd.nextDouble(0, 1000)));
			}
		}
		a.setMap(new CoordinateMap(cities));

		if (rnd.nextDouble() < 0.5) {
			Double maxWeight = a.getItemCollection().asList().stream().collect(Collectors.summingDouble(Item::getWeight));
			a.setMaxWeight((int) (maxWeight * Random.getInstance().nextDouble()));
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
		a.setItems(mItems);
	}

}
