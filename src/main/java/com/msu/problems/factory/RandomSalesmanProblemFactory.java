package com.msu.problems.factory;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.msu.problems.SalesmanProblem;
import com.msu.thief.model.CoordinateMap;
import com.msu.util.Random;

/**
 * This class is used to create a Map which only contains a cost matrix. There
 * is a Map with different points (2D with X and Y values) and the Euclidean
 * distance between this points is used as a edge cost value.
 *
 */
public class RandomSalesmanProblemFactory extends ASalesmanProblemFactory{


	//! max value for a city on a map - X and Y value!
	protected int maxCoordinates = 1000;


	@Override
	public SalesmanProblem create(int numOfCities, Random rnd) {
		ArrayList<Point2D> cities = new ArrayList<Point2D>(numOfCities);
		for (int i = 0; i < numOfCities; i++) {
			Point2D point = new Point2D.Double(rnd.nextInt(0, maxCoordinates), rnd.nextInt(0, maxCoordinates));
			//System.out.println(point);
			cities.add(point);
		}
		return new SalesmanProblem(new CoordinateMap(cities));
	}



	public int getMaxCoordinates() {
		return maxCoordinates;
	}

	public void setMaxCoordinates(int maxCoordinates) {
		this.maxCoordinates = maxCoordinates;
	}
	
	
	



	

}
