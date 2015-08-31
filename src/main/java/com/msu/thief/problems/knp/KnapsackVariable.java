package com.msu.thief.problems.knp;

import com.msu.moo.model.AbstractVariable;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.thief.model.packing.PackingList;

public class KnapsackVariable extends AbstractVariable<PackingList<?>> {

	public KnapsackVariable(PackingList<?> obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new KnapsackVariable(obj);
	}

}
