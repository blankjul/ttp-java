package com.msu.thief.variable;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.model.variables.Variable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;

public class TTPVariable extends Variable<Pair<Tour<?>, PackingList<?>>> {

	public TTPVariable(String s) {
		super(null);
		String[] values = s.split(";");
		this.obj = Pair.create(new StandardTour(values[0]), new BooleanPackingList(values[1]));
	}

	public TTPVariable(List<Integer> t, List<Boolean> b) {
		super(Pair.create(new StandardTour(t), new BooleanPackingList(b)));
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
		return new TTPVariable((Tour<?>) obj.first.copy(), (PackingList<?>) obj.second.copy());
	}

	public Tour<?> getTour() {
		return obj.first;
	}

	public PackingList<?> getPackingList() {
		return obj.second;
	}

	@Override
	public boolean isEqual(Pair<Tour<?>, PackingList<?>> o1, Pair<Tour<?>, PackingList<?>> o2) {
		return o1.first.encode().equals(o2.first.encode()) && o1.second.encode().equals(o2.second.encode());
	}

}
