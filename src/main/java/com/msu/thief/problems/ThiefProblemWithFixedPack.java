package com.msu.thief.problems;

import java.util.List;

import com.msu.moo.model.AProblem;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

/**
 * This problem is a sub problem of the SingleObjectiveThiefProblem. The tour is
 * fixed for all evaluations and as a variable only the packing plan needs to be
 * provided.
 *
 */
public class ThiefProblemWithFixedPack extends AProblem<Tour>  {

	//! tour which is used for evaluations
	protected Pack pack;

	//! problem which underlies this fixed tour problem
	protected AbstractThiefProblem problem;

	
	public ThiefProblemWithFixedPack(AbstractThiefProblem problem, Pack pack) {
		this.problem = problem;
		this.pack = pack;
	}

	protected void evaluate_(Tour var, List<Double> objectives, List<Double> constraintViolations) {
		Solution<TTPVariable> s = problem.evaluate(new TTPVariable(var, pack));
		objectives.addAll(s.getObjectives());
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

	
}
