package com.msu.thief.ea.operators;

import com.msu.moo.interfaces.IMutation;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;
import com.msu.thief.problems.variable.Tour;

public class TourSimpleSwapMutation implements IMutation<Tour> {



	@Override
	public void mutate(Tour p, MyRandom rand) {
		final int i = rand.nextInt(1,p.numOfCities());
		final int j = rand.nextInt(1,p.numOfCities());
		swap(p, i, j);
	}
	

	public static void swap(Tour p, int i, int j) {
		Util.swap(p.decode(), i, j);
	}
	
	
}
