package com.msu.thief.variable.tour.factory;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AVariableFactory;
import com.msu.moo.util.Random;
import com.msu.problems.ThiefProblem;
import com.msu.thief.variable.tour.Tour;

public abstract class ATourFactory extends AVariableFactory {

	@Override
	public Tour<?> next(IProblem problem, Random rand) {
		Tour<?> tour=  next_(problem, rand);
		tour = ThiefProblem.rotateToCityZero(tour, false);
		return tour;
	}

	
	protected abstract Tour<?> next_(IProblem problem, Random rand);
	

	
}
