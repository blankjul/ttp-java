package com.msu.thief.ea;

import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.util.MyRandom;

public interface Factory<T> {
	
	public void initialize(AbstractThiefProblem problem, MyRandom rand);
	
	public T create();
	
	public boolean hasNext();
	
}
