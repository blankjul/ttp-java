package com.msu.thief.problems.tsp;

import com.msu.moo.model.AbstractVariable;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.thief.model.tour.Tour;

public class TSPVariable extends AbstractVariable<Tour<?>> {

	public TSPVariable(Tour<?> obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new TSPVariable(obj);
	}

}
