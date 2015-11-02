package com.msu.problems.factory;

import com.msu.moo.util.Random;
import com.msu.problems.SalesmanProblem;

public abstract class ASalesmanProblemFactory {

	public abstract SalesmanProblem create(int numOfCities, Random rand);
	
}
