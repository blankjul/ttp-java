package com.msu.thief.ea.tour.factory;

import com.msu.thief.ea.AFactory;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;

public abstract class TourFactory extends AFactory<Tour> {

	public TourFactory(AbstractThiefProblem problem, MyRandom rand) {
		super(problem, rand);
	}
	
	
}
