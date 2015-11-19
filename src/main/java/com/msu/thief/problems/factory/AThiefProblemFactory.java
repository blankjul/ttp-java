package com.msu.thief.problems.factory;

import com.msu.thief.problems.ThiefProblem;
import com.msu.util.Random;

public abstract class AThiefProblemFactory {

	public abstract ThiefProblem create(int numOfCities, int itemsPerCity, double maxWeightPerc, Random rand);
	
}
