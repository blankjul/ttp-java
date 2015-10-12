package com.msu.problems.factory;

import com.msu.problems.ThiefProblem;

public abstract class AThiefProblemFactory {

	public abstract ThiefProblem create(int numOfCities, int itemsPerCity, double maxWeightPerc);
	
}
