package com.msu.thief.ea.operators;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.IMutation;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Pair;
import com.msu.thief.ea.RecombinationUtil;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.variable.Tour;

public class ThiefSwapMutation implements IMutation<Tour> {


	
	@Override
	public void mutate(Tour p, MyRandom rand) {
		Pair<Integer, Integer> bounds = RecombinationUtil.calcBounds(rand, 1, p.numOfCities());
		swap(p, bounds.first, bounds.second);
		
	}
	
	public static double swapDeltaTime(Tour t, int i, int j, double timeForTour, SymmetricMap map) {
		
		if (j < i) {
			int tmp = i;
			i = j;
			j = tmp;
		}
		
		final int numOfCities = map.getSize();
		
		double time = timeForTour;
		
		// if swap over all cities time does not change
		if (i == 0 && j >= numOfCities - 1) {
			return timeForTour;
		}
		
		// get the position before swap x x _ s s s x x x
		final int beforeSwap = (i != 0) ? i - 1 : numOfCities - 1;
		time -= map.get(t.ith(beforeSwap),t.ith(i));
		
		// get the position after swap x x s s s s _ x x
		final int afterSwap = (j + 1) % numOfCities;
		time -= map.get(t.ith(j),t.ith(afterSwap));
		
		time += map.get(t.ith(beforeSwap), t.ith(j));
		time += map.get(t.ith(i), t.ith(afterSwap));
		
		return time;
		
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
