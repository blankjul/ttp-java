package com.msu.thief.problems;

import java.util.List;

import org.apache.log4j.Logger;

import com.msu.thief.evaluator.PackingInformation;
import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;

public class ThiefProblem extends AbstractThiefProblem {
	
	static final Logger logger = Logger.getLogger(ThiefProblem.class);
	

	public ThiefProblem() {
		super();
	}

	public ThiefProblem(SymmetricMap map, ItemCollection<Item> items, int maxWeight) {
		super(map, items, maxWeight);
	}
	

	@Override
	protected void evaluate_(TTPVariable var, List<Double> objectives, List<Double> constraintViolations) {
		
		// check for the correct input before using evaluator
		Tour<?> tour = var.getTour();
		PackingList<?> pack = var.getPackingList();
		
		checkTour(tour);
		checkPackingList(pack);
		
		// fix the starting city if necessary
		if (startingCityIsZero) tour = rotateToCityZero(tour, true);
		
		// use the evaluators to calculate the result
		
		TourInformation tourInfo = evalTime.evaluate_(this, tour, pack);
		objectives.add(tourInfo.getTime());
		
		PackingInformation packInfo = evalProfit.evaluate_(this, tour, pack, tourInfo);
		objectives.add(- packInfo.getProfit());
		
		// look for constraints
		final double weight = packInfo.getWeight();
		if (weight <= getMaxWeight()) constraintViolations.add(0d);
		else  constraintViolations.add(weight - getMaxWeight()); 
		
	}
	
	@Override
	public int getNumberOfObjectives() {
		return 2;
	}
	
	
	@Override
	public int getNumberOfConstraints() {
		return 1;
	}
	

	
}
