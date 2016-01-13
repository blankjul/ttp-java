package com.msu.thief.problems.variable;

import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.model.variables.Variable;
import com.msu.util.Pair;

/**
 * This class represents the TTP variable which is necessary to calculate a
 * given solution according to a problem instance.
 *
 */
public class TTPVariable extends Variable<Pair<Tour, Pack>> {

	public TTPVariable(List<Integer> t, List<Boolean> b) {
		super(Pair.create(new Tour(t), new Pack(b)));
	}

	public TTPVariable(Tour t, Pack b) {
		super(Pair.create(t, b));
	}

	public TTPVariable(Pair<Tour, Pack> obj) {
		super(obj);
	}

	@Override
	public String toString() {
		return String.format("%s;%s", getTour(), getPack());
	}

	@Override
	public IVariable copy() {
		return new TTPVariable(getTour().copy(), getPack().copy());
	}

	public Tour getTour() {
		return obj.first;
	}

	public Pack getPack() {
		return obj.second;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getTour() == null) ? 0 : getTour().hashCode());
		result = prime * result + ((getPack() == null) ? 0 : getPack().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject)
			return true;
		if (getClass() != otherObject.getClass())
			return false;
		TTPVariable other = (TTPVariable) otherObject;

		if (!getTour().equals(other.getTour()))
			return false;
		if (!getPack().equals(other.getPack()))
			return false;

		return true;

	}

	public static TTPVariable createFromString(String s) {
		String[] values = s.split(";");
		return new TTPVariable(Tour.createFromString(values[0]), Pack.createFromBooleanString(values[1]));
	}

	
	public static TTPVariable create(Tour t, Pack b) {
		return new TTPVariable(t, b);
	}
}
