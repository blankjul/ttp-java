package com.msu.tsp.util.distances;

import java.awt.geom.Point2D;

public class ManhattenDistance extends ADistanceCalculator {

	@Override
	public double getDistance(Point2D p1, Point2D p2) {
		double disX = Math.abs(p1.getX() - p2.getX());
		double disY = Math.abs(p1.getY() - p2.getY());
		return disX + disY;
	}
}
