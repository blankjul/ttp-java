package com.msu.thief.variable.pack.factory;

import com.msu.interfaces.IProblem;
import com.msu.thief.variable.pack.PackingList;
import com.msu.util.MyRandom;

public class FixedPackingListFactory extends APackingListFactory {

	PackingList<?> pack;
	
	
	public FixedPackingListFactory(PackingList<?> pack) {
		super();
		this.pack = pack;
	}


	@Override
	public PackingList<?> next(IProblem p, MyRandom rand) {
		return (PackingList<?>) pack.copy();
	}

}
