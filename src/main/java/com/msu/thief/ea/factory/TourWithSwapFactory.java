package com.msu.thief.ea.factory;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.variable.Tour;

public class TourWithSwapFactory implements IFactory<Tour>{

	protected Tour starting = null;

	
	public TourWithSwapFactory(Tour starting) {
		super();
		this.starting = starting;
	}


	@Override
	public Tour next(MyRandom rand) {
		Tour result = starting.copy();
		if (rand.nextDouble() < 0.9)
			new TourSwapMutation().mutate(result, rand);
		return result;
	}



	@Override
	public boolean hasNext() {
		return true;
	}

}
