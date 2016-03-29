package com.msu.thief.ea.factory;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;


public class TourOptimalFactory extends AOperator implements IFactory<Tour> {

	
	public TourOptimalFactory(AbstractThiefProblem thief) {
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
