package com.msu.thief.factory.map;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.util.Random;
import com.msu.thief.model.SymmetricMap;

/**
 * This class is used to create a Map which only contains a cost matrix. There
 * is a Map with different points (2D with X and Y values) and the euclidean
 * distance between this points is used as a edge cost value.
 *
 */
public class MapFactory extends AbstractMapFactory {
	
	// type for rounding
	public static enum TYPE {EUCL_2D, CEIL_2D, MANHATTEN_2D};
	
	// type which should be used
	protected TYPE type = TYPE.EUCL_2D;

	// ! maximal coordinate on the map
	protected int maximalValue = 1000;

	protected Random rnd = Random.getInstance();
	
	
	
	public MapFactory() {
		super();
	}


	public MapFactory(int maximalValue) {
		super();
		this.maximalValue = maximalValue;
	}

	

	public SymmetricMap createFromDouble(List<Point2D> cities) {
		SymmetricMap m = new SymmetricMap(cities.size());
		for (int i = 0; i < cities.size(); i++) {
			for (int j = i; j < cities.size(); j++) {
				double value = -1;
				if (type == TYPE.EUCL_2D) {
					double disX = (cities.get(i).getX() - cities.get(j).getX()) * (cities.get(i).getX() - cities.get(j).getX());
					double disY = (cities.get(i).getY() - cities.get(j).getY()) * (cities.get(i).getY() - cities.get(j).getY());
					value = Math.sqrt(disX + disY);
				} else if (type == TYPE.MANHATTEN_2D) {
					value = Math.abs(cities.get(i).getX() - cities.get(j).getX())
							+ Math.abs(cities.get(i).getX() - cities.get(j).getX());
				}
				
				// if ceil round up
				if (type == TYPE.CEIL_2D) value = Math.ceil(value);
				
				// if not EUCL_2D is calculated yet
				m.set(i, j, value);
			}
		}
		return m;
	}
	
	public SymmetricMap create(List<Point> cities) {
		SymmetricMap m = new SymmetricMap(cities.size());
		for (int i = 0; i < cities.size(); i++) {
			for (int j = i; j < cities.size(); j++) {
				double disX = (cities.get(i).getX() - cities.get(j).getX()) * (cities.get(i).getX() - cities.get(j).getX());
				double disY = (cities.get(i).getY() - cities.get(j).getY()) * (cities.get(i).getY() - cities.get(j).getY());
				double value = Math.sqrt(disX + disY);
				
				// if ceil round up
				if (type == TYPE.CEIL_2D) value = Math.ceil(value);
				
				// if not EUCL_2D is calculated yet
				
				m.set(i, j, value);
			}
		}
		return m;
	}
		
		
		
	@Override
	public SymmetricMap create(int n) {
		
		ArrayList<Point> cities = new ArrayList<Point>(n);
		for (int i = 0; i < n; i++) {
			cities.add(new Point(rnd.nextInt(0, maximalValue), rnd.nextInt(0, maximalValue)));
		}
		return create(cities);
		
	}
	
	
	public TYPE getType() {
		return type;
	}


	public void setType(TYPE type) {
		this.type = type;
	}
	

}
