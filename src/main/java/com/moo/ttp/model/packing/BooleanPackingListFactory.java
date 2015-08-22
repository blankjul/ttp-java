package com.moo.ttp.model.packing;

import java.util.ArrayList;
import java.util.List;

import com.moo.ttp.util.Rnd;
import com.msu.moo.model.interfaces.IFactory;

public class BooleanPackingListFactory implements IFactory<PackingList<?>>{

	//! length of the tour
	protected int length;
	
	
	public BooleanPackingListFactory(int length) {
		super();
		this.length = length;
	}


	@Override
	public PackingList<?> create() {
		double pickingProb = Rnd.rndDouble();
		List<Boolean> b = new ArrayList<Boolean>();
		for (int i = 0; i < length; i++) {
			b.add(Rnd.rndDouble() < pickingProb);
		}
		return new BooleanPackingList(b);
	}



}
