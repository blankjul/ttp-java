package com.msu.thief.evolving;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.variables.Variable;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.ThiefProblem;
import com.msu.util.Random;
import com.msu.util.Util;

public class ThiefProblemCrossover extends AbstractCrossover<ThiefProblem>{


	@SuppressWarnings("unchecked")
	@Override
	protected List<ThiefProblem> crossover_(ThiefProblem a, ThiefProblem b, IProblem problem, Random rand) {
		
		ThiefProblem child1 = Util.cloneObject(a);
		ThiefProblem child2 = Util.cloneObject(b);
		
		
		Variable<List<Point2D>> paCities = new Variable<List<Point2D>>(((CoordinateMap) a.getMap()).getCities());
		Variable<List<Point2D>> pbCities = new Variable<List<Point2D>>(((CoordinateMap) b.getMap()).getCities());
		List<IVariable> offCities = new UniformCrossover<>().crossover(paCities, pbCities, rand);
		child1.setMap(new CoordinateMap(((Variable<List<Point2D>>) offCities.get(0)).get()));
		child2.setMap(new CoordinateMap(((Variable<List<Point2D>>) offCities.get(1)).get()));
		
		if (rand.nextDouble() < 0.5) {
			Double maxWeight = a.getItemCollection().asList().stream().collect(Collectors.summingDouble(Item::getWeight));
			child1.setMaxWeight((int) (maxWeight * rand.nextDouble()));
			child2.setMaxWeight((int) (maxWeight * rand.nextDouble()));
		} else {
			child1.setMaxWeight(a.getMaxWeight());
			child2.setMaxWeight(b.getMaxWeight());
		}
		
		
		ItemCollection<Item> paItems = a.getItemCollection();
		ItemCollection<Item> pbItems = b.getItemCollection();
		ItemCollection<Item> child1Items = new ItemCollection<>();
		ItemCollection<Item> child2Items = new ItemCollection<>();
		for (int i = 0; i < a.numOfCities(); i++) {
			for (int j = 0; j < paItems.getItemsFromCity(i).size(); j++) {
				Item item1 = paItems.getItemsFromCity(i).get(j);
				Item item2 = pbItems.getItemsFromCity(i).get(j);
				
				if (rand.nextDouble() < 0.5) {
					child1Items.add(i, new Item(item1.getProfit(), item1.getWeight()));
					child2Items.add(i, new Item(item2.getProfit(), item2.getWeight()));
				} else {
					child1Items.add(i, new Item(item2.getProfit(), item2.getWeight()));
					child2Items.add(i, new Item(item1.getProfit(), item1.getWeight()));
				}		
			}
		}
		child1.setItems(child1Items);
		child2.setItems(child2Items);
		
		return Arrays.asList(child1, child2);
	}
	
	
}
