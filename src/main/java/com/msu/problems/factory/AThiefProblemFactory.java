package com.msu.problems.factory;

import com.msu.moo.util.Random;
import com.msu.problems.ThiefProblem;

public abstract class AThiefProblemFactory {

	public abstract ThiefProblem create(int numOfCities, int itemsPerCity, double maxWeightPerc, Random rand);
	
}
