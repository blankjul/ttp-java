package com.msu.thief.ea.tour.crossover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.thief.ea.RecombinationUtil;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class OrderedCrossover implements TourCrossover{

	
	@Override
	public List<Tour> crossover(AbstractThiefProblem problem, MyRandom rand, Tour t1, Tour t2) {
		
		Pair<Integer, Integer> bounds = RecombinationUtil.calcBounds(rand, 1, t1.numOfCities());
		
		List<Tour> off = new ArrayList<>();
		off.add(crossover(t1, t2, bounds));
		off.add(crossover(t2, t1, bounds));
		
		return off;
	}
	
	
	public Tour crossover(Tour primary, Tour fillup, Pair<Integer, Integer> bounds) {
		
		List<Integer> result = new ArrayList<>();
		
		// create a hash set of all mapped values
		Set<Integer> hash = new HashSet<>();
		for (int i = bounds.first; i < bounds.second; i++) {
			hash.add(primary.ith(i));
			result.add(primary.ith(i));
		}
		
		for (int i = 0; i < primary.numOfCities(); i++) {
			int idx = (i + bounds.second) % primary.numOfCities();
			int city = fillup.ith(idx);
			if (!hash.contains(city) && city != 0) result.add(city);
		}
		
		Collections.rotate(result, bounds.first - 1);
		result.add(0,0);
		
		return new Tour(result);
		
	}
	
	
	

}
