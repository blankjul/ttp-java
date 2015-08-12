package com.moo.ttp.problems;

/**
 * This interface defines the values of a problem
 * 
 * Each problem needs to have a evaluation method that
 * has a predefined input I and output o
 * @param <I> input value
 * @param <O> input value
 */
public interface Problem<I,O> {
	
	public O evaluate(I input);

}
