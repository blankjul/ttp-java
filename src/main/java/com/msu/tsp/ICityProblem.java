package com.msu.tsp;

import com.msu.moo.interfaces.IProblem;
import com.msu.thief.model.SymmetricMap;

/**
 * This interface ensures that the problem has a method
 * which returns the number of cities.
 *
 */
public interface ICityProblem extends IProblem {
	
	public int numOfCities();
	public SymmetricMap getMap();
	public double getMaxSpeed();
	
}
