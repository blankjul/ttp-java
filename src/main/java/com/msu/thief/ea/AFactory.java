package com.msu.thief.ea;

import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.util.MyRandom;

public abstract class AFactory<T> implements Factory<T> {

	
	protected AbstractThiefProblem problem; 
	protected MyRandom rand;
	
	
	public AFactory(AbstractThiefProblem problem, MyRandom rand) {
		super();
		this.problem = problem;
		this.rand = rand;
	}
	
	
	
	
}
