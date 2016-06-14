package com.msu.thief.util.distances;

import java.awt.geom.Point2D;

public class EuclideanDistance extends ADistanceCalculator {

	
	@Override
	public double getDistance(Point2D p1, Point2D p2) {
		double disX = Math.pow((p1.getX() - p2.getX()), 2);
		double disY = Math.pow((p1.getY() - p2.getY()), 2);
		return Math.sqrt(disX + disY);
	}

}
