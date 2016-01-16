package com.msu.thief.ea.tour.factory;

import com.msu.interfaces.IFactory;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;


public class ThiefOptimalTourFactory extends AOperator implements IFactory<Tour> {

	
	public ThiefOptimalTourFactory(AbstractThiefProblem thief) {
		super(thief);
		best = AlgorithmUtil.calcBestTour(thief);
		sym = best.getSymmetric();
	}

	
	protected Tour best;
	
	protected Tour sym;
	
	

	@Override
	public Tour next(MyRandom rand) {
		if (rand.nextDouble() < 0.5) return best.copy();
		else return sym.copy();
	}

	
	@Override
	public boolean hasNext() {
		return true;
	}




}
