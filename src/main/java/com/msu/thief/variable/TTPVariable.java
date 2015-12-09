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
		String tour = (obj.first == null) ? "" :  obj.first.toString();
		return String.format("%s;%s", tour ,obj.second.toString());
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((obj.first == null) ? 0 : obj.first.hashCode());
		result = prime * result + ((obj.second == null) ? 0 : obj.second.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject)
			return true;
		if (!super.equals(otherObject))
			return false;
		if (getClass() != otherObject.getClass())
			return false;
		TTPVariable other = (TTPVariable) otherObject;
		if (obj.second == null) {
			if (other.obj.second != null)
				return false;
		} else if (!obj.second.equals(other.obj.second))
			return false;
		if (obj.first == null) {
			if (other.obj.first != null)
				return false;
		} else if (!obj.first.equals(other.obj.first))
			return false;
		return true;
	}


	
	
	
	

}
