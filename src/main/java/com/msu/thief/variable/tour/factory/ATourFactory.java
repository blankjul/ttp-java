package com.msu.thief.variable.tour.factory;

import com.msu.interfaces.IProblem;
import com.msu.model.AVariableFactory;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public abstract class ATourFactory extends AVariableFactory {

	@Override
	public Tour<?> next(IProblem problem, MyRandom rand) {
		Tour<?> tour=  next_(problem, rand);
		tour = AbstractThiefProblem.rotateToCityZero(tour, false);
		return tour;
	}

	
	protected abstract Tour<?> next_(IProblem problem, MyRandom rand);
	

	
}
