package com.moo.operators.crossover;

import java.util.List;

/**
 * This is an abstract Crossover of an object. This class which inherits from this one
 * could specify which type is expected to be used to crossover. Otherwise there will be an error thrown.
 *
 * @param <T> type which could be mutated!
 */
public abstract class AbstractCrossover<T> {

	@SuppressWarnings("unchecked")
	public List<T> crossover(Object a, Object b) {
		T objA = null;
		T objB = null;
		
		try {
			objA = (T) a;
			objB = (T) b;
	    } catch (ClassCastException e) {
	    	throw new RuntimeException("This Mutation is not allowed for this variable!");
	    }
		
		return crossover_(objA, objB);
	}
	
	abstract protected List<T> crossover_(T a, T b);


}
