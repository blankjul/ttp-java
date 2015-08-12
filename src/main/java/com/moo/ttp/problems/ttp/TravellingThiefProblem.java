package com.moo.ttp.problems.ttp;

import com.moo.ttp.problems.ttp.profit.ProfitCalculator;
import com.moo.ttp.problems.ttp.time.TimeCalculator;
import com.moo.ttp.util.Factory;
import com.moo.ttp.util.Pair;

public class TravellingThiefProblem {

	TravellingThiefProblemSettings settings = null;
	
	ProfitCalculator pc = null;
	
	TimeCalculator tc = null;

	public TravellingThiefProblem(TravellingThiefProblemSettings settings) {
		this.settings = settings;
		pc = Factory.create(ProfitCalculator.class, settings.getProfitCalculator());
		tc = Factory.create(TimeCalculator.class, settings.getTimeCalculator());
	}

	public int numOfCities() {
		return settings.getMap().getSize();
	}

	public int numOfItems() {
		return settings.getItems().size();
	}
	
	@Override
	public String toString() {
		return settings.toString();
	}


	public Pair<Double, Double> evaluate(Integer[] pi, Boolean[] b) {

		tc.run(this, pi, b);
		
		if (tc.getWeight() > settings.getMaxWeight()) return Pair.create(tc.getTime(), 0d);
		double profit = pc.run(settings.getItems().getItems(), tc.getItemTimes());
		
		if (profit < 0) throw new RuntimeException("Profit has to be larger than 0! But it is " + profit);

		return Pair.create(tc.getTime(), profit);

	}

	
	public TravellingThiefProblemSettings getSettings() {
		return settings;
	}
	
	

}
