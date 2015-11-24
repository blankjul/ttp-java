package com.msu.thief.variable.pack.factory;

import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.util.MyRandom;

public class RandomPoolPackingListFactory extends APackingPlanFactory {


	protected List<Integer> pool;
	
	
	public RandomPoolPackingListFactory(List<Integer> pool) {
		super();
		this.pool = pool;
	}


	@Override
	public PackingList<?> next(IProblem p, MyRandom rand) {
		final int length 
	}


}
