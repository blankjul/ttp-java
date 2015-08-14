package com.moo.ttp.model.tour;

import java.util.List;

import com.moo.ttp.model.ICopyable;

/**
 * This class represents the interface of a tour. Since there are different representation of tours 
 * which could be all encoded to the permutation representation.
 */
public interface Tour extends ICopyable<Tour> {

	/**
	 * Returns the internal representation which is in the normal case also
	 * a list of integers. But if it should be different in the size, constrains and so on!
	 * @return internal representation
	 */
	public Object get();
	
	
	/**
	 * Set the internal representation
	 * @param obj internal representation
	 * @return instance using object
	 */
	public void set(Object obj);
	
	
	/**
	 * Encode the given tour from the implemented structure.
	 *  e.g. [0,2,1,4,3]
	 * @return an array without any duplicates and has all the cities.
	 */
	public List<Integer> encode();
	

	/**
	 * Create a random tour is specific to the underlying structure.
	 * @param numOfCities how many cities could be visited by the salesman
	 * @return create a random new tour.
	 */
	public Tour random(int numOfCities);
	
	

	
}