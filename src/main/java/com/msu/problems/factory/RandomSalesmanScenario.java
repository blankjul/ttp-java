package com.msu.problems.factory;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.msu.moo.util.Random;
import com.msu.thief.model.CoordinateMap;
import com.msu.thief.model.SymmetricMap;

/**
 * This class is used to create a Map which only contains a cost matrix. There
 * is a Map with different points (2D with X and Y values) and the Euclidean
 * distance between this points is used as a edge cost value.
 *
 */
public class RandomSalesmanScenario {

	//! max value for a city on a map - X and Y value!
	public static final int MAXIMAL_VALUE_ON_MAP = 1000;



	public static SymmetricMap create(int numOfCities) {
		Random rnd = Random.getInstance();
		ArrayList<Point2D> cities = new ArrayList<Point2D>(numOfCities);
		for (int i = 0; i < numOfCities; i++) {
			Point2D point = new Point2D.Double(rnd.nextInt(0, MAXIMAL_VALUE_ON_MAP), rnd.nextInt(0, MAXIMAL_VALUE_ON_MAP));
			//System.out.println(point);
			cities.add(point);
		}
		return new CoordinateMap(cities);
	}




	

}
