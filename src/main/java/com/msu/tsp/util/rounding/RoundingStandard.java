package com.msu.tsp.util.rounding;

public class RoundingStandard implements IRounding{


	@Override
	public double execute(double d) {
		return Math.round(d);
	}

}
