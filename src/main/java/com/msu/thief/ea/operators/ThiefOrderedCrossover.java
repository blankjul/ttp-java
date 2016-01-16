package com.msu.thief.ea.operators;

import java.util.ArrayList;
import java.util.Collections;
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
	
	
	public Tour crossover(Tour primary, Tour fillup, Pair<Integer, Integer> bounds) {
		
		List<Integer> result = new ArrayList<>();
		
		// create a hash set of all mapped values
		List<Integer> fixed = new ArrayList<>();
		Set<Integer> hash = new HashSet<>();
		for (int i = bounds.first; i < bounds.second; i++) {
			int idx = primary.ith(i);
			hash.add(idx);
			fixed.add(idx);
			result.add(idx);
		}
		
		
		
		for (int i = 0; i < primary.numOfCities(); i++) {
			int idx = (i + bounds.second) % primary.numOfCities();
			int city = fillup.ith(idx);
			if (!hash.contains(city) && city != 0) result.add(city);
		}
		
		Collections.rotate(result, bounds.first - 1);
		result.add(0,0);
		
		Tour t = new Tour(result);
		
		if (!t.isPermutation()) {
			System.out.println();
		}
		
		return t;
		
	}
	
	
	

}
