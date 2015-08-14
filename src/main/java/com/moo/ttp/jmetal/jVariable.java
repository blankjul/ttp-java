package com.moo.ttp.jmetal;

import java.util.Arrays;

import com.moo.ttp.model.packing.BooleanPackingList;
import com.moo.ttp.model.packing.IPackingList;
import com.moo.ttp.model.tour.ITour;
import com.moo.ttp.model.tour.StandardTour;


public class jVariable {

	public ITour tour;
	public IPackingList b;

	public jVariable(int numOfCities, int numOfItems) {
		this.tour = new StandardTour(null).random(numOfCities);
		this.b = new BooleanPackingList(null).random(numOfItems);
	}

	public jVariable(ITour tour, IPackingList b) {
		super();
		this.tour = tour;
		this.b = b;
	}

	public jVariable deepCopy() {
		return new jVariable(tour.copy(), b.copy());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(tour.encode().toString());
		sb.append(b.encode().toString());
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		jVariable that = (jVariable) o;

		if (!tour.equals(that.tour))
			return false;
		if (!b.equals(that.b))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
	    int result = Arrays.hashCode(tour.encode().toArray());
		result = 31 * result + b.hashCode();
		return result;
	}

}
