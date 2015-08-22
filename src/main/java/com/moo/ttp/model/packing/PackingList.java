package com.moo.ttp.model.packing;

import java.util.List;

import com.moo.ttp.model.AVariable;

public abstract class APackingList<T> extends AVariable<APackingList<T>, T, List<Boolean>> implements IPackingList{

	public APackingList() {
		super();
	}

	public APackingList(T obj) {
		super(obj);
	}
	
	
}