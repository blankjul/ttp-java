package com.moo.ttp.model.tour;

import java.util.List;

import com.moo.ttp.model.AVariable;

/**
 * This class represents the interface of a tour. Since there are different representation of tours 
 * which could be all encoded to the permutation representation.
 */
public abstract class ATour<T> extends AVariable<ATour<T>, T, List<Integer>> implements ITour{

	public ATour() {
		super();
	}

	public ATour(T obj) {
		super(obj);
	}

	

}