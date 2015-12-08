package com.msu.thief.problems;

import java.util.List;

import com.msu.thief.evaluator.profit.NoDroppingEvaluator;
import com.msu.thief.evaluator.time.StandardTimeEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;

public class SingleObjectiveThiefProblem extends ThiefProblem {

	protected double R = 1;
	
	protected boolean swtichToMultiObjective = false;
	

	public SingleObjectiveThiefProblem(ThiefProblem problem, double R) {
		this.evalProfit = problem.evalProfit;
		this.evalTime = problem.evalTime;
		this.items = problem.items;
		this.map = problem.map;
		this.maxSpeed = problem.maxSpeed;
		this.maxWeight = problem.maxWeight;
		this.minSpeed = problem.minSpeed;
		this.name = problem.getName();
		this.R = R;
	}
	
	public SingleObjectiveThiefProblem() {
		this.evalProfit = new NoDroppingEvaluator();
	}

	public SingleObjectiveThiefProblem(SymmetricMap map, ItemCollection<Item> items, int maxWeight, double R) {
		super(map, items, maxWeight);
		this.evalProfit = new NoDroppingEvaluator();
		this.R = R;
	}

	
	@Override
	public int getNumberOfObjectives() {
		if (swtichToMultiObjective) return 2;
		else return 1;
	}
	

	@Override
	protected void evaluate_(TTPVariable var, List<Double> objectives, List<Double> constraintViolations) {
		
		if (swtichToMultiObjective) {
			super.evaluate_(var, objectives, constraintViolations);
			return;
		}
		
		// always start at city 0
		Pair<Tour<?>, PackingList<?>> pair = var.get();
		if (startingCityIsZero) pair.first = ThiefProblem.rotateToCityZero(pair.first, true);

		checkTour(pair.first);
		//checkPackingList(pair.second);

		// use the evaluators to calculate the result
		evalTime = new StandardTimeEvaluator(this);
		double time = evalTime.evaluate(pair);
		// check if the maximal weight constraint is violated
		if (evalTime.getWeight() > getMaxWeight()) {
			objectives.add(Double.MAX_VALUE);
			constraintViolations.add(evalTime.getWeight() - getMaxWeight());
			return;
		}

		double profit = evalProfit.evaluate(evalTime.getItemMap());
		double value = profit - R * time;

		// return the negative because we minimize all!
		objectives.add(-value);
		constraintViolations.add(0d);
		
	}
	

	public double getR() {
		return R;
	}

	public void setR(double r) {
		R = r;
	}
	
	public ThiefProblem getThiefProblem() {
		ThiefProblem problem = new ThiefProblem();
		problem.evalProfit = this.evalProfit;
		problem.evalTime = this.evalTime;
		problem.items = this.items;
		problem.map = this.map;
		problem.maxSpeed = this.maxSpeed;
		problem.maxWeight = this.maxWeight;
		problem.minSpeed = this.minSpeed;
		problem.setName(name);
		return problem;
	}

	public boolean isSwtichedToMultiObjective() {
		return swtichToMultiObjective;
	}

	public void setToMultiObjective(boolean swtichToMultiObjective) {
		this.swtichToMultiObjective = swtichToMultiObjective;
	}
	
	

}
