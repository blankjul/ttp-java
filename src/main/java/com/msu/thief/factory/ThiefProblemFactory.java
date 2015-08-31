package com.msu.thief.factory;

import com.msu.thief.factory.items.ItemFactory;
import com.msu.thief.factory.map.MapFactory;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.problems.TravellingThiefProblem;

/**
* This class represents a thief factory which allows to create a TravellingThief Problem.
 */


public class ThiefProblemFactory {

	// ! this defines which dropping for the items is chosen
	public static enum DROPPING_TYPE {
		RANDOM, MAX_DISTANCE_DEPENDENT
	};
	
	// ! default dropping type is max distance for a well formed pareto front
	protected DROPPING_TYPE dropType = DROPPING_TYPE.MAX_DISTANCE_DEPENDENT;
	
	protected MapFactory facMap = null;
	
	protected ItemFactory facItems = null;
	
	protected Double maxWeightPerc = null;
	
	protected String name = null;
	
	

	public ThiefProblemFactory(MapFactory facMap, ItemFactory facItems, Double maxWeightPerc) {
		super();
		this.facMap = facMap;
		this.facItems = facItems;
		this.maxWeightPerc = maxWeightPerc;
	}
	
	
	public ThiefProblemFactory(MapFactory facMap, ItemFactory facItems, Double maxWeightPerc, String name) {
		super();
		this.facMap = facMap;
		this.facItems = facItems;
		this.maxWeightPerc = maxWeightPerc;
		this.name = name;
	}







	public TravellingThiefProblem create(int numOfCities, int itemsPerCity) {
		
		// create the map
		TravellingThiefProblem problem = new TravellingThiefProblem();
		problem.setMap(facMap.create(numOfCities));

		// create the items
		ItemCollection<Item> items = new ItemCollection<Item>();

		long sumWeights = 0;
		Double maxTime = 0d;
		
		// set the maximal tour time or null
		if (dropType == DROPPING_TYPE.MAX_DISTANCE_DEPENDENT) {
			maxTime = (problem.getMap().getMax() * problem.getMap().getSize()) / problem.MIN_SPEED;
		} else maxTime = null;
		facItems.setMaximalTourTime(maxTime);
		
		for (int i = 0; i < numOfCities; i++) {
			for (int j = 0; j < itemsPerCity; j++) {
				Item item = facItems.create();
				sumWeights += item.getWeight();
				items.add(i, item);
			}
		}

		problem.setItems(items);
		problem.setMaxWeight((int) (sumWeights * maxWeightPerc));
		problem.setName(name);
		return problem;
	}



	public DROPPING_TYPE getDropType() {
		return dropType;
	}



	public void setDropType(DROPPING_TYPE dropType) {
		this.dropType = dropType;
	}


	public MapFactory getFacMap() {
		return facMap;
	}


	public void setFacMap(MapFactory facMap) {
		this.facMap = facMap;
	}


	public ItemFactory getFacItems() {
		return facItems;
	}



	public void setFacItems(ItemFactory facItems) {
		this.facItems = facItems;
	}


	public Double getMaxWeightPerc() {
		return maxWeightPerc;
	}


	public void setMaxWeightPerc(Double maxWeightPerc) {
		this.maxWeightPerc = maxWeightPerc;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	

}
