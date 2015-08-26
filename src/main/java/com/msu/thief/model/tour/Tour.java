package com.msu.thief.model.tour;

import java.util.List;

import com.msu.moo.model.AbstractVariable;

/**
 * This class represents the interface of a tour. Since there are different representation of tours 
 * which could be all encoded to the permutation representation.
 */
public abstract class Tour<T> extends AbstractVariable<T> {

	
	public Tour(T obj) {
		super(obj);
	}
	
	abstract public List<Integer> encode();
	

	
}