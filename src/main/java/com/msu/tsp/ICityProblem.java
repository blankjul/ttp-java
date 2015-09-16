package com.msu.tsp;

import com.msu.moo.model.interfaces.IProblem;

/**
 * This interface ensures that the problem has a method
 * which returns the number of cities.
 *
 */
public interface ICityProblem extends IProblem {
	
	public int numOfCities();
	
}
