package com.msu.thief.ea.pack.factory;

import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.problems.variable.Pack;


public class ThiefOptimalPackFactory extends PackFactory {


	@Override
	public Pack create() {
		final int maxWeight = (int) (rand.nextDouble() * problem.getMaxWeight());
		return AlgorithmUtil.calcBestPackingPlan(problem.getItems(), maxWeight);
	}

	
	@Override
	public boolean hasNext() {
		return true;
	}
	


}
