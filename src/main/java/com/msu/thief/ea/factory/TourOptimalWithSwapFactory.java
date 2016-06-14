package com.msu.thief.ea.factory;

import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;

public class TourOptimalWithSwapFactory extends TourOptimalFactory {

	
	protected Tour starting = null;

	public TourOptimalWithSwapFactory(AbstractThiefProblem thief) {
		super(thief);
	}
	
	

	@Override
	public Tour next(MyRandom rand) {
		Tour result = null;
		if (rand.nextDouble() < 0.5)
			result = best.copy();
		else
			result = sym.copy();

		if (rand.nextDouble() < 0.9)
			new TourSwapMutation().mutate(result, rand);

		return result;
	}

}
