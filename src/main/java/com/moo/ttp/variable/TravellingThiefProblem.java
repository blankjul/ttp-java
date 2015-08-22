package com.moo.ttp.variable;

import com.moo.ttp.TravellingThiefProblemSettings;
import com.moo.ttp.calculator.profit.ProfitCalculator;
import com.moo.ttp.calculator.time.TimeCalculator;
import com.moo.ttp.model.packing.PackingList;
import com.moo.ttp.model.tour.Tour;
import com.moo.ttp.util.Factory;
import com.moo.ttp.util.Pair;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.interfaces.IProblem;

public class TravellingThiefProblem implements IProblem<TTPVariable, TravellingThiefProblem>{

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


	public Pair<Double, Double> evaluate(Tour<?> t,PackingList<?> b) {

		tc.run(this, t.encode(), b.encode());
		if (tc.getWeight() > settings.getMaxWeight()) {
			return Pair.create(tc.getTime(), 0d);
		}
		
		double profit = pc.run(settings.getItems().getItems(), tc.getItemTimes());
		if (profit < 0) throw new RuntimeException("Profit has to be larger than 0! But it is " + profit);

		return Pair.create(tc.getTime(), profit);

	}

	
	public TravellingThiefProblemSettings getSettings() {
		return settings;
	}

	@Override
	public Evaluator<TTPVariable, TravellingThiefProblem> getEvaluator() {
		return new TTPEvaluator(this);
	}
	public ProfitCalculator getProfitCalculator() {
		return pc;
	}

	public TimeCalculator getTimeCalculator() {
		return tc;
	}

	
	

}
