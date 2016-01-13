package com.msu.thief.ea;

import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.util.MyRandom;

public abstract class AFactory<T> implements Factory<T> {

	
	protected AbstractThiefProblem problem; 
	protected MyRandom rand;
	

	@Override
	public void initialize(AbstractThiefProblem problem, MyRandom rand) {
		this.problem = problem;
		this.rand = rand;
	}
	

	
}
