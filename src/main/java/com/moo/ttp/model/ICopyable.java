package com.moo.ttp.model;

/**
 * This interface ensures that a deep copy of an object is possible.
 */
public interface ICopyable<T> {
	
	/**
	 * Deep clone the whole object!
	 * @return deep clone of the object.
	 */
	public T copy();

}
