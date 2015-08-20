package com.moo.model;

/**
 * Every algorithm has a problem as Input 
 */
public interface IAlgorithm {
	
	public <T, I, O> NonDomintedSet<T> solve(Problem<I,O> p);

}
