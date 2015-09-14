package com.msu.thief.variable;

import java.util.ArrayList;
import java.util.List;

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
	public String toString() {
		List<Integer> p = new ArrayList<>();
		for(boolean b : obj.second.encode()) {
			if (b == true) p.add(1);
			else p.add(0);
		}
		return String.format("%s;%s", obj.first.toString(),p.toString());
	}

	@Override
	public IVariable copy() {
		return new TTPVariable(obj);
	}

	
}
