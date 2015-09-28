package com.msu.thief.variable;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.model.PackingList;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AVariable;
import com.msu.moo.util.Pair;
import com.msu.tsp.model.Tour;


public class TTPVariable extends AVariable<Pair<Tour<?>, PackingList<?>>> {
	
	
	public TTPVariable(Tour<?> t, PackingList<?> b) {
		super(Pair.create(t, b));
	}
	
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
	
	public Tour<?> getTour() {
		return obj.first;
	}
	
	public PackingList<?> getPackingList() {
		return obj.second;
	}

	
}
