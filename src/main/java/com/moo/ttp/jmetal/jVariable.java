package com.moo.ttp.jmetal;

import java.util.Arrays;

import jmetal.core.Variable;

import com.moo.ttp.util.Util;

public class jVariable extends Variable {
	

	public Integer[] pi; 
	public Boolean[] b;
	

	public jVariable(int numOfCities, int numOfItems) {
		this.pi = Util.createRandomTour(numOfCities);
		this.b = Util.createRandomPickingPlan(numOfItems);
	}
	
	public jVariable(Integer[] pi, Boolean[] b) {
		super();
		this.pi = pi;
		this.b = b;
	}

	@Override
	public Variable deepCopy() {
		return new jVariable(pi.clone(), b.clone());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Arrays.toString(pi));
		int[] bInt = new int[b.length];
		for (int i = 0; i < bInt.length; i++) {
			if (b[i]) bInt[i] = 1;
			else bInt[i] = 0;
		}
		sb.append( Arrays.toString(bInt));
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		jVariable that = (jVariable) o;

		if (!pi.equals(that.pi))
			return false;
		if (!b.equals(that.b))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = Arrays.hashCode(pi);
		result = 31 * result + b.hashCode();
		return result;
	}
	
	

}
