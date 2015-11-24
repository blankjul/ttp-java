package com.msu.thief.variable.pack;

import java.util.List;
import java.util.Set;

import com.msu.model.variables.Variable;

public abstract class PackingList<T> extends Variable<List<Boolean>> {

	
	public PackingList(List<Boolean> obj) {
		super(obj);
	}
	
	public String toString() {
		return obj.toString();
	}

	abstract public List<Boolean> encode();
	

	abstract public Set<Integer> toIndexSet();
	
	
}