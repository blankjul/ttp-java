package com.moo.ttp.problems.travellingthiefproblem;


public class ProfitCalculatorFactory {

	public static ProfitCalculator create(String fullType) {
		Class<?> c;
		try {
			c = Class.forName(fullType);
			ProfitCalculator pc = (ProfitCalculator) c.newInstance();
			return pc;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
