package com.msu.thief.factory.map;

import java.awt.Point;
import java.util.ArrayList;

import com.msu.moo.util.Random;
import com.msu.thief.model.Map;

/**
 * This class is used to create a Map which only contains a cost matrix. There
 * is a Map with different points (2D with X and Y values) and the euclidean
 * distance between this points is used as a edge cost value.
 *
 */
public class MapFactory extends AbstractMapFactory {

	// ! maximal coordinate on the map
	protected int maximalValue = 1000;

	
	public MapFactory() {
		super();
	}


	public MapFactory(int maximalValue) {
		super();
		this.maximalValue = maximalValue;
	}



	@Override
	public Map create(int n) {
		Random rnd = Random.getInstance();

		ArrayList<Point> cities = new ArrayList<Point>(n);
		for (int i = 0; i < n; i++) {
			cities.add(new Point(rnd.nextInt(0, maximalValue), rnd.nextInt(0, maximalValue)));
		}

		Map m = new Map(n);
		for (int i = 0; i < cities.size(); i++) {
			for (int j = i; j < cities.size(); j++) {
				double disX = (cities.get(i).getX() - cities.get(j).getX()) * (cities.get(i).getX() - cities.get(j).getX());
				double disY = (cities.get(i).getY() - cities.get(j).getY()) * (cities.get(i).getY() - cities.get(j).getY());
				m.set(i, j, Math.sqrt(disX + disY));
			}
		}
		return m;
	}

}
