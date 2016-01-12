package com.msu.thief.problems;

import java.util.List;

import org.apache.log4j.Logger;

import com.msu.thief.evaluator.PackingInformation;
import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefProblem extends AbstractThiefProblem {
	
	static final Logger logger = Logger.getLogger(ThiefProblem.class);
	

	public ThiefProblem() {
		super();
	}

	public ThiefProblem(SymmetricMap map, ItemCollection<Item> items, int maxWeight) {
		super(map, items, maxWeight);
	}
	
	
	@Override
	public int getNumberOfObjectives() {
		return 2;
	}
	
	
	@Override
	public int getNumberOfConstraints() {
		return 1;
	}


	
	@Override
	protected void evaluate_(TTPVariable var, List<Double> objectives, List<Double> constraintViolations) {
		
		Tour t = var.getTour();
		Pack p = var.getPack();
		
		t.validate(this.numOfCities());
		p.validate(this.numOfItems());
		
		TourInformation tourInfo = evalTime.evaluate_(this, t, p);
		objectives.add(tourInfo.getTime());
		
		PackingInformation packInfo = evalProfit.evaluate_(this, t, p, tourInfo);
		objectives.add(- packInfo.getProfit());

		final double weight = packInfo.getWeight();
		constraintViolations.add(Math.max(0, weight - getMaxWeight()));
		
	}
	

	
}
