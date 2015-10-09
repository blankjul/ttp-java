package com.msu.util.rounding;

public class RoundingSimpleCast implements IRounding{

	@Override
	public double execute(double d) {
		return Double.valueOf((int) d);
	}

}
