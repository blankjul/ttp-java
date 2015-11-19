package com.msu.thief.variable.tour.factory;

import com.msu.interfaces.IProblem;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Random;

public class FixedTourFactory extends ATourFactory {

	protected Tour<?> t = null;

	public FixedTourFactory(Tour<?> t) {
		super();
		this.t = t;
	}

	@Override
	protected Tour<?> next_(IProblem problem, Random rand) {
		return t;
	}

}
