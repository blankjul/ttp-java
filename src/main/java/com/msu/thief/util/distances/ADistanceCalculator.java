package com.msu.thief.util.distances;

import java.awt.Point;
import java.awt.geom.Point2D;

public abstract class ADistanceCalculator {

	public abstract double getDistance(Point2D p1, Point2D p2);

	public double getDistance(Point p1, Point p2) {
		return getDistance(new Point2D.Double(p1.getX(), p1.getY()), 
				new Point2D.Double(p2.getX(), p2.getY()));
	}
}
