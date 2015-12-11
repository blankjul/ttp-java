package com.msu.thief.problems;

import java.util.List;

import com.msu.thief.variable.TTPVariable;

public class SingleObjectiveThiefProblem extends AbstractThiefProblem  {

	protected double R = 1;
	protected boolean swtichToMultiObjective = false;
	
	
	public SingleObjectiveThiefProblem() {
		super();
	}


	public SingleObjectiveThiefProblem(AbstractThiefProblem problem, double R) {
		this.evalProfit = problem.evalProfit;
		this.evalTime = problem.evalTime;
		this.items = problem.items;
		this.map = problem.map;
		this.maxSpeed = problem.maxSpeed;
		this.maxWeight = problem.maxWeight;
		this.minSpeed = problem.minSpeed;
		this.setName(problem.getName());
		this.R = R;
	}



	@Override
	public int getNumberOfObjectives() {
		if (swtichToMultiObjective) return 2;
		else return 1;
	}
	
	
	@Override
	public int getNumberOfConstraints() {
		return 1;
	}


	@Override
	protected void evaluate_(TTPVariable var, List<Double> objectives, List<Double> constraintViolations) {
		
		// calculate the multi-objective result
		getThiefProblem().evaluate_(var, objectives, constraintViolations);
		
		// if switched to multi we are done
		if (swtichToMultiObjective) return;
		
		final double time = objectives.get(0);
		final double profit = -objectives.get(1);
		
		objectives.clear();
		double value = profit - R * time;
		// return the negative because we minimize all!
		objectives.add(-value);

		
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
