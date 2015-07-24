package com.moo.ttp.jmetal;

import org.uma.jmetal.solution.Solution;

/**
 * This is the interface of the object that is used for all 
 * the jMetal operations.
 *
 */
public interface jISolution extends Solution<jVariable> {
	
	/** 
	 * Removes for the given solution the constraint violations
	 */
	public void removeConstraintViolations();
	
}
