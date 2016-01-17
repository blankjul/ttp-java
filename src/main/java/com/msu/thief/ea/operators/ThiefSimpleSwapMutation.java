package com.msu.thief.ea.operators;

import com.msu.moo.interfaces.IMutation;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;

public class ThiefSimpleSwapMutation extends AOperator implements IMutation<Tour> {


	public ThiefSimpleSwapMutation(AbstractThiefProblem thief) {
		super(thief);
	}


	@Override
	public void mutate(Tour p, MyRandom rand) {
		final int i = rand.nextInt(p.numOfCities());
		final int j = rand.nextInt(p.numOfCities());
		swap(p, i, j);
	}
	

	public static void swap(Tour p, int i, int j) {
		Util.swap(p.decode(), i, j);
	}
	
	
}
