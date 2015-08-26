package com.msu.thief.variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.model.AbstractProblem;
import com.msu.moo.util.Pair;
import com.msu.thief.evaluator.profit.ProfitCalculator;
import com.msu.thief.evaluator.time.TimeCalculator;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.TravellingThiefProblemSettings;
import com.msu.thief.util.Factory;

public class TravellingThiefProblem extends AbstractProblem<TTPVariable>{

	TravellingThiefProblemSettings settings = null;
	
	ProfitCalculator pc = null;
	
	TimeCalculator tc = null;
	
	protected String name;

	
	protected TravellingThiefProblem() {
		super();
	}

	public TravellingThiefProblem(TravellingThiefProblemSettings settings) {
		this.settings = settings;
		pc = Factory.create(ProfitCalculator.class, settings.getProfitCalculator());
		tc = Factory.create(TimeCalculator.class, settings.getTimeCalculator());
		name = String.format("TTP-c%s-i%s", numOfCities(), numOfItems());
	}
	
	

	public int numOfCities() {
		return settings.getMap().getSize();
	}

	public int numOfItems() {
		return settings.getItems().size();
	}
	
	@Override
	public String toString() {
		return name;
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

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	
	

}
