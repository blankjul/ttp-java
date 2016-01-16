package com.msu.thief.problems.variable;

import com.msu.interfaces.IVariable;

/**
 * This class represents the TTP variable which is necessary to calculate a
 * given solution according to a problem instance.
 *
 */
public class TTPVariable implements IVariable {

	//! tour of the thief
	protected Tour tour;
	
	//! packing plan
	protected Pack pack;
	
	
	public TTPVariable(Tour tour, Pack pack) {
		super();
		this.tour = tour;
		this.pack = pack;
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
		return tour;
	}

	public Pack getPack() {
		return pack;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pack == null) ? 0 : pack.hashCode());
		result = prime * result + ((tour == null) ? 0 : tour.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TTPVariable other = (TTPVariable) obj;
		if (pack == null) {
			if (other.pack != null)
				return false;
		} else if (!pack.equals(other.pack))
			return false;
		if (tour == null) {
			if (other.tour != null)
				return false;
		} else if (!tour.equals(other.tour))
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
