package com.moo.ttp.jmetal;

import java.util.Arrays;

import com.moo.ttp.model.tour.Tour;
import com.moo.ttp.util.Util;

public class jVariable {

	public Tour tour;
	public Boolean[] b;

	public jVariable(int numOfCities, int numOfItems) {
		this.tour = tour.random(numOfCities);
		this.b = Util.createRandomPickingPlan(numOfItems);
	}

	public jVariable(Tour tour, Boolean[] b) {
		super();
		this.tour = tour;
		this.b = b;
	}

	public jVariable deepCopy() {
		return new jVariable(tour.copy(), b.clone());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(tour.encode().toString());
		int[] bInt = new int[b.length];
		for (int i = 0; i < bInt.length; i++) {
			if (b[i])
				bInt[i] = 1;
			else
				bInt[i] = 0;
		}
		sb.append(Arrays.toString(bInt));
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
