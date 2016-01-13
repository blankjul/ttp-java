package com.msu.thief.algorithms.tour;

import java.util.List;

import com.msu.model.AProblem;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class FixedTourSingleObjectiveThiefProblem extends AProblem<Pack>{

	protected AbstractThiefProblem problem;
	
	//! tour which is fixed for that problem
	protected Tour tour;
	
	
	public FixedTourSingleObjectiveThiefProblem(AbstractThiefProblem problem, Tour tour) {
		super();
		this.problem = problem;
		this.tour = tour;
	}

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}
	
	

	@Override
	public int getNumberOfConstraints() {
		return problem.getNumberOfConstraints();
	}

	@Override
	protected void evaluate_(Pack var, List<Double> objectives, List<Double> constraintViolations) {
		Solution s = problem.evaluate(new TTPVariable(tour, var));
		objectives.addAll(s.getObjectives());
		constraintViolations.addAll(s.getConstraintViolations());
	}

	public AbstractThiefProblem getProblem() {
		return problem;
	}

	public void setProblem(AbstractThiefProblem problem) {
		this.problem = problem;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	
	
	
}
