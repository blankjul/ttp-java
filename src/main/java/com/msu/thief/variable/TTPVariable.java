package com.msu.thief.variable;

import com.msu.moo.model.AbstractVariable;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.util.Pair;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.Tour;

public class TTPVariable extends AbstractVariable<Pair<Tour<?>, PackingList<?>>> {

	public TTPVariable(Pair<Tour<?>, PackingList<?>> obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new TTPVariable(obj);
	}
	
	@Override
	public String toString() {
		return String.format("(%s,%s)", obj.first.toString(),obj.second.toString());
	}

}
