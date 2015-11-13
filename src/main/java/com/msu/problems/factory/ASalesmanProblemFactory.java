package com.msu.problems.factory;

import com.msu.problems.SalesmanProblem;
import com.msu.util.Random;

public abstract class ASalesmanProblemFactory {

	public abstract SalesmanProblem create(int numOfCities, Random rand);
	
}
