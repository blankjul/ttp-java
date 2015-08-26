package com.msu.thief.model.packing;

import java.util.List;

import com.msu.moo.model.AbstractVariable;

public abstract class PackingList<T> extends AbstractVariable<List<Boolean>> {

	
	public PackingList(List<Boolean> obj) {
		super(obj);
	}
	
	public String toString() {
		return obj.toString();
	}

	abstract public List<Boolean> encode();
	

	
	
}