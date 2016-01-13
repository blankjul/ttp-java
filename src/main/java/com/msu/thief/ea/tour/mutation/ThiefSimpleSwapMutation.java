package com.msu.thief.ea.tour.mutation;

import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class ThiefSimpleSwapMutation implements TourMutation {


	@Override
	public void mutate(AbstractThiefProblem problem, MyRandom rand, Tour p) {
		final int i = rand.nextInt(p.numOfCities());
		final int j = rand.nextInt(p.numOfCities());
		swap(p, i, j);
	}
	

	public static void swap(Tour p, int i, int j) {
		Util.swap(p.encode(), i, j);
	}
	
	
}
