package com.moo.ttp.variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.moo.ttp.TravellingThiefProblemSettings;
import com.moo.ttp.calculator.profit.ProfitCalculator;
import com.moo.ttp.calculator.time.TimeCalculator;
import com.moo.ttp.model.packing.PackingList;
import com.moo.ttp.model.tour.Tour;
import com.moo.ttp.util.Factory;
import com.moo.ttp.util.Pair;
import com.msu.moo.model.AbstractProblem;

public class TravellingThiefProblem extends AbstractProblem<TTPVariable>{

	TravellingThiefProblemSettings settings = null;
	
	ProfitCalculator pc = null;
	
	TimeCalculator tc = null;

	
	protected TravellingThiefProblem() {
		super();
	}

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

	
	@Override
	protected List<Double> evaluate_(TTPVariable variable) {
		
		List<Integer> tour = variable.get().first.encode();
		List<Boolean> packing = variable.get().second.encode();
		
		tc.run(this, tour, packing);
		
		if (tc.getWeight() > settings.getMaxWeight()) {
			return new ArrayList<Double>(Arrays.asList(tc.getTime(), 0d));
		}
		
		double profit = pc.run(settings.getItems().getItems(),tc.getItemTimes());
		
		if (profit < 0) throw new RuntimeException("Profit has to be larger than 0! But it is " + profit);

		return new ArrayList<Double>(Arrays.asList(tc.getTime(), - profit));
		
	}
	
	
	public TravellingThiefProblemSettings getSettings() {
		return settings;
	}

	
	
	public ProfitCalculator getProfitCalculator() {
		return pc;
	}

	public TimeCalculator getTimeCalculator() {
		return tc;
	}

	
	

}
