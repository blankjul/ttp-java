package com.msu.thief.util.rounding;

public class RoundingCeil implements IRounding{

	@Override
	public double execute(double d) {
		return Math.ceil(d);
	}

}
