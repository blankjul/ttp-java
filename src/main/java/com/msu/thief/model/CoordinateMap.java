package com.msu.thief.model;

import java.awt.geom.Point2D;
import java.util.List;

import com.msu.tsp.util.distances.ADistanceCalculator;
import com.msu.tsp.util.distances.EuclideanDistance;

public class CoordinateMap extends SymmetricMap {

	// ! if the map is created with coordinates this is known
	protected List<Point2D> cities = null;

	
	public CoordinateMap(List<Point2D> cities) {
		this(cities, new EuclideanDistance());
	}
	
	/**
	 * Create cities by using a map.
	 * @param cities list with points
	 * @param calcDistance distance calculator method
	 */
	public CoordinateMap(List<Point2D> cities, ADistanceCalculator calcDistance) {
		super(cities.size());
		this.cities = cities;
		for (int i = 0; i < cities.size(); i++) {
			for (int j = i + 1; j < cities.size(); j++) {
				double distance = calcDistance.getDistance(cities.get(i), cities.get(j));
				set(i, j, distance);
			}
		}
	}

	public List<Point2D> getCities() {
		return cities;
	}
	
	

}
