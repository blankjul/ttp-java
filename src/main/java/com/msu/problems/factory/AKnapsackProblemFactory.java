package com.msu.problems.factory;

import com.msu.problems.KnapsackProblem;

public abstract class AKnapsackProblemFactory {

	public abstract KnapsackProblem create(int numOfItems, double maxWeightPerc);
	
}
