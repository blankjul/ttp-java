package com.msu.thief.ea.factory;

import com.msu.interfaces.IFactory;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.util.MyRandom;


public class ThiefPackOptimalFactory extends AOperator implements IFactory<Pack>  {


	
	public ThiefPackOptimalFactory(AbstractThiefProblem thief) {
		super(thief);
	}


	@Override
	public Pack next(MyRandom rand) {
		final int maxWeight = (int) (rand.nextDouble() * thief.getMaxWeight());
		return AlgorithmUtil.calcBestPackingPlan(thief.getItems(), maxWeight);
	}

	
	@Override
	public boolean hasNext() {
		return true;
	}




}
