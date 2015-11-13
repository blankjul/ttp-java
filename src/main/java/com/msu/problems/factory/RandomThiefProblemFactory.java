package com.msu.problems.factory;

import java.util.LinkedList;
import java.util.Queue;

import com.msu.problems.KnapsackProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.evaluator.profit.IndividualProfitEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.util.Random;

/**
 * This class represents a thief factory which allows to create a
 * TravellingThief Problem.
 */

public class RandomThiefProblemFactory extends AThiefProblemFactory{

	
	protected ASalesmanProblemFactory facSalesman = null;
	
	protected AKnapsackProblemFactory facKnapsack = null;

	
	
	public RandomThiefProblemFactory(ASalesmanProblemFactory facSalesman, AKnapsackProblemFactory facKnapsack) {
		this.facSalesman = facSalesman;
		this.facKnapsack = facKnapsack;
	}



	@Override
	public ThiefProblem create(int numOfCities, int itemsPerCity, double maxWeightPerc, Random rand) {

		// create the map
		ThiefProblem problem = new ThiefProblem();
		problem.setMap(facSalesman.create(numOfCities, rand).getMap());

		// create the items
		ItemCollection<Item> items = new ItemCollection<Item>();

		long sumWeights = 0;

		Double maximalTourTime = (problem.getMap().getMax() * problem.getMap().getSize()) / problem.getMinSpeed();

		
		KnapsackProblem knp = facKnapsack.create(numOfCities * itemsPerCity, maxWeightPerc, rand);
		Queue<Item> q = new LinkedList<>(knp.getItems());
		
		for (int i = 0; i < numOfCities; i++) {
			for (int j = 0; j < itemsPerCity; j++) {
				Item item = q.poll();
				sumWeights += item.getWeight();
				double dropping = item.getProfit() / maximalTourTime;
				// double dropping = Random.getInstance().nextDouble(0.2, 0.8);
				items.add(i, new Item(item.getProfit(), item.getWeight(), dropping));
			}
		}

		problem.setItems(items);
		problem.setMaxWeight((int) (sumWeights * maxWeightPerc));
		problem.setProfitEvaluator(new IndividualProfitEvaluator());
		problem.setName(String.format("TTP-%s-%s", numOfCities, itemsPerCity));
		return problem;
	}



}
