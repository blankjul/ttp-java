package com.msu.thief.ea;

import java.util.List;

import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.util.MyRandom;

public interface Crossover<T> {

	public List<T> crossover(AbstractThiefProblem problem, MyRandom rand, T e1, T e2);
	
}
