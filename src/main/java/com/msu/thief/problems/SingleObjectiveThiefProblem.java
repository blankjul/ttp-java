package com.msu.thief.problems;

import java.util.List;

import com.msu.thief.evaluator.PackingInformation;
import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;


public class SingleObjectiveThiefProblem extends AbstractThiefProblem  {

	
	//! weight vector for composing to single objective
	protected double R = 1;
	
	
	
	public SingleObjectiveThiefProblem() {
		super();
	}

	public SingleObjectiveThiefProblem(SymmetricMap map, ItemCollection<Item> items, int maxWeight) {
		super(map, items, maxWeight);
	}
	
	public SingleObjectiveThiefProblem(SymmetricMap map, ItemCollection<Item> items, int maxWeight, double R) {
		this(map, items, maxWeight);
		this.R = R;
	}
	
	@Override
	public int getNumberOfObjectives() {
		return 1;
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
		final double time = tourInfo.getTime();
		
		PackingInformation packInfo = evalProfit.evaluate_(this, t, p, tourInfo);
		final double profit = packInfo.getProfit();

		final double value = profit - R * time;
		objectives.add(- value);

		final double violation = Math.max(0, packInfo.getWeight() - getMaxWeight());
		constraintViolations.add(violation);
		
		
	}
	

	public double getR() {
		return R;
	}

	public void setR(double r) {
		R = r;
	}
	
	




	

}
