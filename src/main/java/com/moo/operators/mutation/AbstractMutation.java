package com.moo.operators.mutation;

/**
 * This is an abstract Mutation of an object. This class which inherits from this one
 * could specify which type is expected to mutate. Otherwise there will be an error thrown.
 *
 * @param <T> type which could be mutated!
 */
public abstract class AbstractMutation<T> {

	@SuppressWarnings("unchecked")
	public void mutate(Object obj) {
		T element = null;
		
		try {
			element = (T) obj;
	    } catch (ClassCastException e) {
	    	throw new RuntimeException("This Mutation is not allowed for this variable!");
	    }
		
		mutate_(element);
	}
	
	abstract protected void mutate_(T element);


}
