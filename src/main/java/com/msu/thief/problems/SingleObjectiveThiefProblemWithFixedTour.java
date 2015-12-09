package com.msu.thief.problems;

import java.util.List;

import com.msu.model.AProblem;
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
public class SingleObjectiveThiefProblemWithFixedTour extends AProblem<PackingList<?>>  implements IPackingProblem, ICityProblem{

	//! tour which is used for evaluations
	protected Tour<?> tour;

	//! problem which underlies this fixed tour problem
	protected SingleObjectiveThiefProblem problem;

	public SingleObjectiveThiefProblemWithFixedTour(SingleObjectiveThiefProblem problem, Tour<?> tour) {
		this.problem = problem;
		this.tour = tour;
	}

	protected void evaluate_(PackingList<?> var, List<Double> objectives, List<Double> constraintViolations) {
		problem.evaluate_(new TTPVariable(tour, var), objectives, constraintViolations);
	}


	@Override
	public int getNumberOfObjectives() {
		return problem.getNumberOfObjectives();
	}

	@Override
	public int getNumberOfConstraints() {
		return problem.getNumberOfConstraints();
	}

	public SingleObjectiveThiefProblem getProblem() {
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
		problem.setToMultiObjective(b);
	}

	
	
}
