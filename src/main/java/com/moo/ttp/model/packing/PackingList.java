package com.moo.ttp.model.packing;

import java.util.List;

import com.msu.moo.model.AbstractVariable;

public abstract class PackingList<T> extends AbstractVariable<List<Boolean>> {

	
	public PackingList(List<Boolean> obj) {
		super(obj);
	}

	abstract public List<Boolean> encode();
	

	
	
}