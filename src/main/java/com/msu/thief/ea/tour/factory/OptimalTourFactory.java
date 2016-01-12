package com.msu.thief.ea.tour.factory;

import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;


public class OptimalTourFactory extends TourFactory {

	
	protected Tour best;
	
	public OptimalTourFactory(AbstractThiefProblem problem, MyRandom rand) {
		super(problem, rand);
		best = AlgorithmUtil.calcBestTour(problem);
	}

	
	@Override
	public Tour create() {
		return best.copy();
	}

	
	@Override
	public boolean hasNext() {
		return true;
	}
	


}
