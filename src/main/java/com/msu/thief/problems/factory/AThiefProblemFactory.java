package com.msu.thief.problems.factory;

import com.msu.thief.problems.ThiefProblem;
import com.msu.util.MyRandom;

public abstract class AThiefProblemFactory {

	public abstract ThiefProblem create(int numOfCities, int itemsPerCity, double maxWeightPerc, MyRandom rand);
	
}
