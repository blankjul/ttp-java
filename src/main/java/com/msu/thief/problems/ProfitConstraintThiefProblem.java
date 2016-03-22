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


public class ProfitConstraintThiefProblem extends AbstractThiefProblem  {

	
	private double minProfitConstraint;
	
	public ProfitConstraintThiefProblem() {
		super();
	}

	public ProfitConstraintThiefProblem(SymmetricMap map, ItemCollection<Item> items, int maxWeight) {
		super(map, items, maxWeight);
	}
	
	
	public ProfitConstraintThiefProblem(AbstractThiefProblem thief, double minProfitConstraint) {
		super(thief.getName(),thief.minSpeed, thief.maxSpeed, thief.evalProfit, thief.evalTime, thief.map, thief.maxWeight, thief.items);
		setMinProfitConstraint(minProfitConstraint);
	}
	

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}


	@Override
	public int getNumberOfConstraints() {
		return 2;
	}
	

	@Override
	protected void evaluate_(TTPVariable var, List<Double> objectives, List<Double> constraintViolations) {
		
		Tour t = var.getTour();
		Pack p = var.getPack();
		
		t.validate(this.numOfCities());
		p.validate(this.numOfItems());
		
		TourInformation tourInfo = evalTime.evaluate_(this, t, p);
		final double time = tourInfo.getTime();
		objectives.add(time);
		

		PackingInformation packInfo = evalProfit.evaluate_(this, t, p, tourInfo);
		final double profit = packInfo.getProfit();

		final double violation = Math.max(0, packInfo.getWeight() - getMaxWeight());
		constraintViolations.add(violation);
		
		final double profitViolation = Math.max(0, minProfitConstraint - profit);
		constraintViolations.add(profitViolation);
		
		
	}

	public double getMinProfitConstraint() {
		return minProfitConstraint;
	}

	public void setMinProfitConstraint(double profitConstraint) {
		this.minProfitConstraint = profitConstraint;
	}
	
	
	

}
