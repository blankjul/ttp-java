package com.moo.ttp.factory;

import java.awt.Point;
import java.util.ArrayList;

import com.moo.ttp.model.Map;
import com.moo.ttp.util.Rnd;

public class MapFactory {
	
	public static Map create(int n, int maxValue) {
		
		ArrayList<Point> cities = new  ArrayList<Point>(n);
		for (int i = 0; i < n; i++) {
			cities.add(new Point(Rnd.rndInt(0, maxValue), Rnd.rndInt(0, maxValue)));
		}
		
		Map m = new Map(n);
		for (int i = 1; i < cities.size(); i++) {
			for (int j = i; j < cities.size(); j++) {
				double disX = (cities.get(i).getX() - cities.get(j).getX()) * (cities.get(i).getX() - cities.get(j).getX());
				double disY = (cities.get(i).getY() - cities.get(j).getY()) * (cities.get(i).getY() - cities.get(j).getY());
				m.set(i, j, Math.sqrt(disX + disY));
			}
		}
		return m;
	}
	
	
	

}
