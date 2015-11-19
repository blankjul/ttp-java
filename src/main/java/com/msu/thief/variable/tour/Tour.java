package com.msu.thief.variable.tour;

import java.util.List;

import com.msu.model.variables.Variable;

/**
 * This class represents the interface of a tour. Since there are different representation of tours 
 * which could be all encoded to the permutation representation.
 */
public abstract class Tour<T> extends Variable<T> {

	
	public Tour(T obj) {
		super(obj);
	}
	
	public String toString() {
		return obj.toString();
	}
	
	abstract public List<Integer> encode();

	abstract public Tour<T> getSymmetric();
	

	
	
}