package com.msu.thief.model;

import java.awt.geom.Point2D;
import java.util.List;

import com.msu.thief.util.distances.ADistanceCalculator;
import com.msu.thief.util.distances.EuclideanDistance;
import com.msu.thief.util.rounding.IRounding;
import com.msu.thief.util.rounding.RoundingCeil;

public class CoordinateMap extends SymmetricMap {

	// ! if the map is created with coordinates this is known
	protected List<Point2D> cities = null;
	
	public CoordinateMap(List<Point2D> cities) {
		this(cities, new EuclideanDistance(), new RoundingCeil());
	}
	
	/**
	 * Create cities by using a map.
	 * @param cities list with points
	 * @param calcDistance distance calculator method
	 */
	public CoordinateMap(List<Point2D> cities, ADistanceCalculator calcDistance, IRounding rounding) {
		super(cities.size());
		this.cities = cities;
		for (int i = 0; i < cities.size(); i++) {
			for (int j = i + 1; j < cities.size(); j++) {
				double distance = calcDistance.getDistance(cities.get(i), cities.get(j));
				set(i, j, distance);
			}
		}
		round(rounding);
	}

	public List<Point2D> getCities() {
		return cities;
	}
	
	

}
