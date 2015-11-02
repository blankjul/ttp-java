package com.msu.util.distances;

import java.awt.geom.Point2D;

import com.msu.util.rounding.RoundingNearestInt;

public class GeographicalDistance extends ADistanceCalculator {

	@Override
	public double getDistance(Point2D p1, Point2D p2) {

		double[] latitude = new double[2];
		double[] longitude = new double[2];

		double deg = new RoundingNearestInt().execute(p1.getX());
		double min = deg - p1.getX();
		latitude[0] = Math.PI * (deg + 5.0 * min / 3.0) / 180d;
		
		deg = new RoundingNearestInt().execute(p1.getY());
		min = p1.getY() - deg;
		longitude[0] = Math.PI * (deg + 5.0 * min / 3.0) / 180d;

		deg = new RoundingNearestInt().execute(p2.getX());
		min = deg - p2.getX();
		latitude[1] = Math.PI * (deg + 5.0 * min / 3.0) / 180d;
		
		deg = new RoundingNearestInt().execute(p2.getY());
		min = p2.getY() - deg;
		longitude[1] = Math.PI * (deg + 5.0 * min / 3.0) / 180d;

		double RRR = 6378.388;

		double q1 = Math.cos(longitude[0] - longitude[1]);
		double q2 = Math.cos(latitude[0] - latitude[1]);
		double q3 = Math.cos(latitude[0] + latitude[1]);

		double dist = (int) (RRR * Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);

		return dist;
	}

}
