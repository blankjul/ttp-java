package com.msu.thief.problems;

import java.util.List;

import com.msu.model.AProblem;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.model.Item;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;

/**
 * This problem is a sub problem of the SingleObjectiveThiefProblem. The tour is
 * fixed for all evaluations and as a variable only the packing plan needs to be
 * provided.
 *
 */
public class ThiefProblemWithFixedTour extends AProblem<PackingList<?>>  implements IPackingProblem, ICityProblem{

	//! tour which is used for evaluations
	protected Tour<?> tour;

	//! problem which underlies this fixed tour problem
	protected AbstractThiefProblem problem;

	public ThiefProblemWithFixedTour(AbstractThiefProblem problem, Tour<?> tour) {
		this.problem = problem;
		this.tour = tour;
	}

	protected void evaluate_(PackingList<?> var, List<Double> objectives, List<Double> constraintViolations) {
		Solution s = problem.evaluate(new TTPVariable(tour, var));
		objectives.addAll(s.getObjective());
		constraintViolations.addAll(s.getConstraintViolations());
	}


	@Override
	public int getNumberOfObjectives() {
		return problem.getNumberOfObjectives();
	}

	@Override
	public int getNumberOfConstraints() {
		return problem.getNumberOfConstraints();
	}

	public AbstractThiefProblem getProblem() {
		return problem;
	}

	@Override
	public int numOfCities() {
		return problem.numOfCities();
	}

	@Override
	public SymmetricMap getMap() {
		return problem.getMap();
	}
	
	public double getMinSpeed() {
		return problem.getMinSpeed();
	}

	@Override
	public double getMaxSpeed() {
		return problem.getMaxSpeed();
	}

	@Override
	public int numOfItems() {
		return problem.numOfItems();
	}

	@Override
	public List<Item> getItems() {
		return problem.getItems();
	}

	@Override
	public int getMaxWeight() {
		return problem.getMaxWeight();
	}

	public Tour<?> getTour() {
		return tour;
	}

	public void setToMultiObjective(boolean b) {
		if (problem instanceof SingleObjectiveThiefProblem) 
			((SingleObjectiveThiefProblem)problem).setToMultiObjective(b);
	}

	
	
}
