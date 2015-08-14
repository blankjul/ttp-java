package com.moo.ttp.model.packing;

import java.util.ArrayList;
import java.util.List;

import com.moo.ttp.util.Rnd;

public class BooleanPackingList extends APackingList<List<Boolean>>{

	
	public BooleanPackingList(List<Boolean> l) {
		super(l);
	}

	@Override
	public APackingList<List<Boolean>> copy() {
		BooleanPackingList p = new BooleanPackingList(new ArrayList<Boolean>(obj));
		return p;
	}

	@Override
	public List<Boolean> encode() {
		return obj;
	}

	@Override
	public APackingList<List<Boolean>> random(int length) {
		double pickingProb = Rnd.rndDouble();
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < length; i++) {
			b.add(Rnd.rndDouble() < pickingProb);
		}
		return new BooleanPackingList(b);
	}



}
