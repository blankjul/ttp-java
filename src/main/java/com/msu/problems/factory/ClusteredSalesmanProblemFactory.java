package com.msu.problems.factory;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.util.Random;
import com.msu.problems.SalesmanProblem;
import com.msu.thief.model.CoordinateMap;

/**
 * This class is used to create a Map which only contains a cost matrix. There
 * is a Map with different points (2D with X and Y values) and the Euclidean
 * distance between this points is used as a edge cost value.
 *
 */
public class ClusteredSalesmanProblemFactory extends RandomSalesmanProblemFactory{


	protected Integer numOfClusters = null;
	
	protected double radius = 100;
	
	
	public ClusteredSalesmanProblemFactory(int numOfClusters) {
		super();
		this.numOfClusters = numOfClusters;
	}


	@Override
	public SalesmanProblem create(int numOfCities, Random rnd) {
		
		
		List<Point2D> clusters = new ArrayList<>();
		for (int i = 0; i < numOfClusters; i++) {
			clusters.add(new Point2D.Double(rnd.nextDouble(0, maxCoordinates), rnd.nextDouble(0, maxCoordinates)));
		}
		
		
		ArrayList<Point2D> cities = new ArrayList<Point2D>(numOfCities);
		cities.add(new Point2D.Double(0,0));
		
		for (int i = 1; i < numOfCities; i++) {
			
			// assign to cluster randomly
			Point2D cluster = clusters.get(rnd.nextInt(clusters.size()));
			
			double X = rnd.nextInt((int) (cluster.getX() - radius), (int) (cluster.getX() - radius));
			double Y = rnd.nextInt((int) (cluster.getY() - radius), (int) (cluster.getY() - radius));
			
			Point2D point = new Point2D.Double(X,Y);
			cities.add(point);
		}
		return new SalesmanProblem(new CoordinateMap(cities));
		
	}

	

}
