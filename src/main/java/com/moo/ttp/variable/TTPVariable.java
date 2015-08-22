package com.moo.ttp.variable;

import com.moo.ttp.model.packing.PackingList;
import com.moo.ttp.model.tour.Tour;
import com.moo.ttp.util.Pair;
import com.msu.moo.model.AbstractVariable;
import com.msu.moo.model.interfaces.IVariable;

public class TTPVariable extends AbstractVariable<Pair<Tour<?>, PackingList<?>>> {

	public TTPVariable(Pair<Tour<?>, PackingList<?>> obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new TTPVariable(obj);
	}

}
