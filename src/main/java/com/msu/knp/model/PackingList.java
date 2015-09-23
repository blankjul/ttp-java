package com.msu.knp.model;

import java.util.List;

import com.msu.moo.model.AVariable;

public abstract class PackingList<T> extends AVariable<List<Boolean>> {

	
	public PackingList(List<Boolean> obj) {
		super(obj);
	}
	
	public String toString() {
		return obj.toString();
	}

	abstract public List<Boolean> encode();
	

	
	
}