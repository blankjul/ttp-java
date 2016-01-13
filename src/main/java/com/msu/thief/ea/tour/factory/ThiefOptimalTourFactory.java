package com.msu.thief.ea.tour.factory;

import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;


public class ThiefOptimalTourFactory extends TourFactory {

	protected Tour best;
	protected Tour sym;
	
	
	@Override
	public void initialize(AbstractThiefProblem problem, MyRandom rand) {
		super.initialize(problem, rand);
		best = AlgorithmUtil.calcBestTour(problem);
		sym = best.getSymmetric();
	}

	@Override
	public Tour create() {
		if (rand.nextDouble() < 0.5) return best.copy();
		else return sym.copy();
	}

	
	@Override
	public boolean hasNext() {
		return true;
	}
	
	


}