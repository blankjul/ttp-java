package com.msu.thief.ea.tour.mutation;

import java.util.ArrayList;
import java.util.List;

import com.msu.thief.ea.RecombinationUtil;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class SwapMutation implements TourMutation {


	@Override
	public void mutate(AbstractThiefProblem problem, MyRandom rand, Tour p) {
		Pair<Integer, Integer> bounds = RecombinationUtil.calcBounds(rand, 1, p.numOfCities());
		swap(p, bounds.first, bounds.second);
		
	}
	

	public static void swap(Tour p, int i, int j) {
		
		// ensure that i < j
		if (j < i) {
			int tmp = i;
			i = j;
			j = tmp;
		}
		
		List<Integer> result = new ArrayList<>(p.numOfCities());
		
		// first part correct order
		for (int k = 0; k < i; k++) {
			result.add(p.ith(k));
		}
		
		// inverse order of the middle
		for (int k = j; k >= i; k--) {
			result.add(p.ith(k));
		}
		
		// last part right order
		for (int k = j + 1; k < p.numOfCities(); k++) {
			result.add(p.ith(k));
		}
		
		// set the result
		p.set(result);
		
	}

	
	
}
