package com.msu.thief.problems.factory;

import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.util.MyRandom;

public abstract class AThiefProblemFactory {

	public abstract AbstractThiefProblem create(int numOfCities, int itemsPerCity, double maxWeightPerc, MyRandom rand);
	
}
