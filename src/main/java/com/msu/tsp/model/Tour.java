package com.msu.tsp.model;

import java.util.List;

import com.msu.moo.model.AVariable;

/**
 * This class represents the interface of a tour. Since there are different representation of tours 
 * which could be all encoded to the permutation representation.
 */
public abstract class Tour<T> extends AVariable<T> {

	
	public Tour(T obj) {
		super(obj);
	}
	
	public String toString() {
		return obj.toString();
	}
	
	abstract public List<Integer> encode();

	

	

	
	
}