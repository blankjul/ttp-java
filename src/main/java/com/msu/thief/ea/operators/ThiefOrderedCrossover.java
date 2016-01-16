package com.msu.thief.ea.operators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.interfaces.ICrossover;
import com.msu.thief.ea.RecombinationUtil;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class ThiefOrderedCrossover implements ICrossover<Tour> {


	@Override
	public List<Tour> crossover(Tour t1, Tour t2, MyRandom rand) {
		
		Pair<Integer, Integer> bounds = RecombinationUtil.calcBounds(rand, 1, t1.numOfCities());
		//Pair<Integer, Integer> bounds =  Pair.create(0, rand.nextInt(t1.numOfCities()));
		
		List<Tour> off = new ArrayList<>();
		off.add(crossover(t1, t2, bounds));
		off.add(crossover(t2, t1, bounds));
		
		return off;
	}
	
	/**
	 * @param bounds including first and excluding last
	 */
	public Tour crossover(Tour primary, Tour fillup, Pair<Integer, Integer> bounds) {
		
		List<Integer> result = new ArrayList<>();
		
		// create a hash set of all mapped values
		List<Integer> fixed = primary.decode().subList(bounds.first, bounds.second);
		Set<Integer> hash = new HashSet<>(fixed);
		
		for (int i = 0; i < primary.numOfCities(); i++) {
			int city = fillup.ith(i);
			if (!hash.contains(city)) result.add(city);
		}
		
		result.addAll(bounds.first, fixed);
		
		Tour t = new Tour(result);
		
		if (!t.isPermutation()) {
			System.out.println();
		}
		
		return t;
		
	}
	
	
	

}
