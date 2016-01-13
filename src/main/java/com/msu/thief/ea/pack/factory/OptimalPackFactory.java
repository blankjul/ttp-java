package com.msu.thief.ea.pack.factory;

import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.util.MyRandom;


public class OptimalPackFactory extends PackFactory {

	public OptimalPackFactory(AbstractThiefProblem problem, MyRandom rand) {
		super(problem, rand);
	}


	@Override
	public Pack create() {
		final int maxWeight = (int) (rand.nextDouble() * problem.getMaxWeight());
		return AlgorithmUtil.calcBestPackingPlan(problem, maxWeight);
	}

	
	@Override
	public boolean hasNext() {
		return true;
	}
	


}
