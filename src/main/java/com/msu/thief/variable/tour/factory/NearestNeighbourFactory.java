package com.msu.thief.variable.tour.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.msu.interfaces.IProblem;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.ICityProblem;
import com.msu.thief.util.CombinatorialUtil;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class NearestNeighbourFactory extends ATourFactory {

	protected int maxPoolSize = 20;
	
	List<Tour<?>> pool = null;
	
	IProblem problem = null;
	
	
	public static Tour<?> create(int startingCity, SymmetricMap map) {
		
		// result including the first city
		List<Integer> tour = new ArrayList<>(Arrays.asList(startingCity));
		
		// visited city as hash
		Set<Integer> visited = new HashSet<>();
		visited.add(startingCity);
		
		// current city for looking in the correct row at the matrix
		int currentCity = startingCity;
		
		// while not all cities visited
		while (visited.size() < map.getSize()) {
			
			// look for the next closest city
			List<Double> distances = new ArrayList<Double>();
			for (int i = 0; i < map.getSize(); i++) {
				if (visited.contains(i)) distances.add(Double.MAX_VALUE);
				else distances.add(map.get(currentCity, i));
			}
			currentCity = distances.indexOf(Collections.min(distances));
			tour.add(currentCity);
			visited.add(currentCity);
			
		}
		return new StandardTour(tour);
	}



	@Override
	public Tour<?> next_(IProblem p, MyRandom rand) {
		
		// if there is a new problem!
		if (problem != p) {
			pool = null;
			problem = p;
		}
		
		if (pool == null) {
			pool = new ArrayList<>();
			SymmetricMap map = ((ICityProblem) problem).getMap();
			List<Integer> startingCities = CombinatorialUtil.getIndexVector(map.getSize());
			rand.shuffle(startingCities);
			Queue<Integer> q = new LinkedList<>(startingCities);
			
			final int poolSize = Math.min(maxPoolSize, startingCities.size());
			for (int i = 0; i < poolSize; i++) {
				pool.add(create(q.poll(), map));
			}
			
		}
		
		
		return pool.get(rand.nextInt(pool.size()));
	}

}
