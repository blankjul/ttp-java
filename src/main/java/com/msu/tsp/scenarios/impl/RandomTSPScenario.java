package com.msu.tsp.scenarios.impl;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.util.Random;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.util.distances.ADistanceCalculator;
import com.msu.tsp.util.distances.EuclideanDistance;

/**
 * This class is used to create a Map which only contains a cost matrix. There
 * is a Map with different points (2D with X and Y values) and the Euclidean
 * distance between this points is used as a edge cost value.
 *
 */
public class RandomTSPScenario {

	//! max value for a city on a map - X and Y value!
	public static final int MAXIMAL_VALUE_ON_MAP = 1000;

	
	public static <T extends Point2D> SymmetricMap create(List<T> cities) {
		return create(cities, new EuclideanDistance());
	}
	
	public static <T extends Point2D> SymmetricMap create(List<T> cities, ADistanceCalculator calcDistance) {
		SymmetricMap m = new SymmetricMap(cities.size());
		for (int i = 0; i < cities.size(); i++) {
			for (int j = i; j < cities.size(); j++) {
				double distance = calcDistance.getDistance(cities.get(i), cities.get(j));
				m.set(i, j, distance);
			}
		}
		return m;
	}
	

	public static SymmetricMap create(int numOfCities) {
		Random rnd = Random.getInstance();
		ArrayList<Point2D> cities = new ArrayList<Point2D>(numOfCities);
		for (int i = 0; i < numOfCities; i++) {
			cities.add(new Point2D.Double(rnd.nextInt(0, MAXIMAL_VALUE_ON_MAP), rnd.nextInt(0, MAXIMAL_VALUE_ON_MAP)));
		}
		return create(cities);
	}




	

}