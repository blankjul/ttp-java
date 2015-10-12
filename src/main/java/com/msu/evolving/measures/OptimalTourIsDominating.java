package com.msu.evolving.measures;

import java.util.List;

import com.msu.algorithms.exhaustive.ThiefExhaustive;
import com.msu.analyze.ThiefAmountOfDifferentTours;
import com.msu.evolving.ThiefProblemVariable;
import com.msu.moo.model.AProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class OptimalTourIsDominating extends AProblem<ThiefProblemVariable>{

	protected int counter = 0;
	
	@Override
	public int getNumberOfObjectives() {
		return 1;
	}
	

	@Override
	public int getNumberOfConstraints() {
		return 1;
	}


	@Override
	protected void evaluate_(ThiefProblemVariable var, List<Double> objectives, List<Double> constraintViolations) {
		NonDominatedSolutionSet set = new ThiefExhaustive().run(var.get());
		int numOfDifferentTour = new ThiefAmountOfDifferentTours().analyze(set);
		
		constraintViolations.add((double) numOfDifferentTour - 2);
		objectives.add(-(double) set.size());
		System.out.println(++counter);
	}
	
	

	

}
