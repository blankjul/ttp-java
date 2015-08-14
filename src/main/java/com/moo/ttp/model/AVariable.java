package com.moo.ttp.model;

/**
 * This class represents the interface of a tour. Since there are different representation of tours 
 * which could be all encoded to the permutation representation.
 * 
 * C Class it self
 * D decoded representation
 * E encoded representation
 */
public abstract class AVariable<C,  D,  E> {

	//! this is the representation that is used for the Tour
	protected D obj;
	
	
	public AVariable() {
		super();
	}

	public AVariable(D obj) {
		super();
		this.obj = obj;
	}


	/**
	 * Returns the internal representation which is in the normal case also
	 * a list of integers. But if it should be different in the size, constrains and so on!
	 * @return internal representation
	 */
	public D get() {
		return obj;
	}
	
	
	/**
	 * Set the internal representation
	 * @param obj internal representation
	 * @return instance using object
	 */
	@SuppressWarnings("unchecked")
	public void set(Object obj) {
		D element = null;
		
		try {
			element = (D) obj;
	    } catch (ClassCastException e) {
	    	throw new RuntimeException("The Value could not be set for this variable!");
	    }
		this.obj = element;
	}
	
	
	/**
	 * Encode the given tour from the implemented structure.
	 * @return new variable
	 */
	abstract public E encode();
	

	/**
	 * Create a variable specific to the underlying structure.
	 * @param length of the encoded representation
	 * @return create a random new variable
	 */
	abstract public C random(int length);
	
	
	
}