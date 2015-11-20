package com.msu.thief.problems.factory;

import com.msu.thief.problems.KnapsackProblem;
import com.msu.util.MyRandom;

public abstract class AKnapsackProblemFactory {

	public abstract KnapsackProblem create(int numOfItems, double maxWeightPerc, MyRandom rand);
	
}
