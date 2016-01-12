package com.msu.thief.ea.pack.factory;

import com.msu.thief.ea.AFactory;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.util.MyRandom;

public abstract class PackFactory extends AFactory<Pack> {

	public PackFactory(AbstractThiefProblem problem, MyRandom rand) {
		super(problem, rand);
	}
	
	
}
