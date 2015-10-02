package com.msu.meta;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.knp.model.Item;
import com.msu.moo.operators.AbstractCrossover;
import com.msu.moo.util.Random;
import com.msu.thief.ThiefProblem;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.ItemCollection;
import com.msu.util.ThiefUtil;

public class FactoryThiefCrossover extends AbstractCrossover<ThiefProblem>{

	@Override
	protected List<ThiefProblem> crossover_(ThiefProblem a, ThiefProblem b) {
		return Arrays.asList(crossoverWithOrder(a, b), crossoverWithOrder(b, a));
	}
	
	protected ThiefProblem crossoverWithOrder(ThiefProblem a, ThiefProblem b) {
		ThiefProblem child = (ThiefProblem) ThiefUtil.cloneObject(a);
		Random rnd = Random.getInstance();
		
		
		List<Point2D> paCities = ((CoordinateMap) a.getMap()).getCities();
		List<Point2D> pbCities = ((CoordinateMap) b.getMap()).getCities();
		
		List<Point2D> cities = new ArrayList<>();
		for (int i = 0; i < a.numOfCities(); i++) {
			if (Random.getInstance().nextDouble() < 0.5) {
				cities.add(paCities.get(i));
			} else {
				cities.add(pbCities.get(i));
			}
		}
		child.setMap(new CoordinateMap(cities));
		
		child.setMaxWeight((a.getMaxWeight() + b.getMaxWeight()) / 2);
		
		
		ItemCollection<Item> paItems = a.getItemCollection();
		ItemCollection<Item> pbItems = b.getItemCollection();
		ItemCollection<Item> mItems = new ItemCollection<>();
		for (int i = 0; i < a.numOfCities(); i++) {
			for (int j = 0; j < paItems.getItemsFromCity(i).size(); j++) {
				Item item = null;
				if (rnd.nextDouble() < 0.5) {
					item = paItems.getItemsFromCity(i).get(j);
				} else {
					 item = pbItems.getItemsFromCity(i).get(j);
				}		
				mItems.add(i, new Item(item.getProfit(), item.getWeight()));
			}
		}
		a.setItems(mItems);
		
		return child;
	}

}
