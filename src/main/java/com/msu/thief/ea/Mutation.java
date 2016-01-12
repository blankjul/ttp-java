package com.msu.thief.ea;

import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.util.MyRandom;

public interface Mutation<T> {

	public void mutate(AbstractThiefProblem problem, MyRandom rand, T element);
	
}
