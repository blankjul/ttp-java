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
public class ThiefProblemWithFixedPacking extends AProblem<Tour<?>>  implements IPackingProblem, ICityProblem{

	//! tour which is used for evaluations
	protected PackingList<?> pack;

	//! problem which underlies this fixed tour problem
	protected AbstractThiefProblem problem;

	
	public ThiefProblemWithFixedPacking(AbstractThiefProblem problem, PackingList<?> pack) {
		this.problem = problem;
		this.pack = pack;
	}

	protected void evaluate_(Tour<?> var, List<Double> objectives, List<Double> constraintViolations) {
		Solution s = problem.evaluate(new TTPVariable(var, pack));
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

	public PackingList<?> getPackingList() {
		return pack;
	}

	public void setToMultiObjective(boolean b) {
		if (problem instanceof SingleObjectiveThiefProblem) 
			((SingleObjectiveThiefProblem)problem).setToMultiObjective(b);
	}

	
	
}
