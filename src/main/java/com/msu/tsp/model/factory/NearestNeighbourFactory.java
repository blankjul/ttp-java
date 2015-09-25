package com.msu.tsp.model.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.msu.moo.util.Random;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;
import com.msu.util.CombinatorialUtil;

public class NearestNeighbourFactory<P extends ICityProblem> extends ATourFactory<P>{

	protected int maxPoolSize = 20;
	
	List<Tour<?>> pool = null;
	
	P problem = null;
	
	
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
	public Tour<?> next(P p) {
		
		// if there is a new problem!
		if (problem != p) {
			pool = null;
			problem = p;
		}
		
		if (pool == null) {
			pool = new ArrayList<>();
			SymmetricMap map = problem.getMap();
			List<Integer> startingCities = CombinatorialUtil.getIndexVector(map.getSize());
			Random.getInstance().shuffle(startingCities);
			Queue<Integer> q = new LinkedList<>(startingCities);
			
			final int poolSize = Math.min(maxPoolSize, startingCities.size());
			for (int i = 0; i < poolSize; i++) {
				pool.add(create(q.poll(), map));
			}
			
		}
		
		
		return pool.get(Random.getInstance().nextInt(pool.size()));
	}

}