package com.msu.problems.factory;

import com.msu.problems.KnapsackProblem;
import com.msu.util.Random;

public abstract class AKnapsackProblemFactory {

	public abstract KnapsackProblem create(int numOfItems, double maxWeightPerc, Random rand);
	
}
