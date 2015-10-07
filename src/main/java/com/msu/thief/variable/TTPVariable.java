package com.msu.thief.variable;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.PackingList;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AVariable;
import com.msu.moo.util.Pair;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;

public class TTPVariable extends AVariable<Pair<Tour<?>, PackingList<?>>> {

	
	public TTPVariable(String s) {
		super(null);
		String[] values = s.split(";");
		this.obj = Pair.create(new StandardTour(values[0]), new BooleanPackingList(values[1]));
	}
	
	
	public TTPVariable(Tour<?> t, PackingList<?> b) {
		super(Pair.create(t, b));
	}

	public TTPVariable(Pair<Tour<?>, PackingList<?>> obj) {
		super(obj);
	}

	@Override
	public String toString() {
		List<Integer> p = new ArrayList<>();
		for (boolean b : obj.second.encode()) {
			if (b == true)
				p.add(1);
			else
				p.add(0);
		}
		return String.format("%s;%s", obj.first.toString(), p.toString());
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


	@Override
	public boolean isEqual(Pair<Tour<?>, PackingList<?>> o1, Pair<Tour<?>, PackingList<?>> o2) {
			return o1.first.encode().equals(o2.first.encode()) && 
					o1.second.encode().equals(o2.second.encode()) ;
	}




}
