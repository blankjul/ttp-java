package com.msu.tsp.scenarios;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.msu.thief.model.SymmetricMap;
import com.msu.thief.scenarios.AScenario;
import com.msu.tsp.model.Tour;
import com.msu.tsp.scenarios.impl.RandomTSPScenario;
import com.msu.tsp.util.rounding.RoundingNearestInt;

public abstract class ACoordinateScenario extends AScenario<SymmetricMap, Tour<?>> {

	//!  and Y coordinates of the cities
	protected abstract double[][] getCoordinates();

	
	@Override
	public SymmetricMap getObject() {
		return getMap(getCoordinates());
	}


	public static SymmetricMap getMap(double[][] coordinates) {
		List<Point2D> cities = new ArrayList<>();
		for (double[] p : coordinates) {
			cities.add(new Point2D.Double(p[0], p[1]));
		}
		SymmetricMap map = RandomTSPScenario.create(cities);
		map.round(new RoundingNearestInt());
		return map;
	}
	

}
