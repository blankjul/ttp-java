package com.moo.ttp.model.packing;

import java.util.ArrayList;
import java.util.List;

public class BooleanPackingList extends PackingList<List<Boolean>>{

	
	public BooleanPackingList(List<Boolean> l) {
		super(l);
	}

	@Override
	public PackingList<List<Boolean>> copy() {
		BooleanPackingList p = new BooleanPackingList(new ArrayList<Boolean>(obj));
		return p;
	}

	@Override
	public List<Boolean> encode() {
		return obj;
	}



}
