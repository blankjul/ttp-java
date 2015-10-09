package com.msu.evolving.measures;

import java.util.List;

import com.msu.algorithms.exhaustive.ThiefExhaustive;
import com.msu.analyze.DifferentToursInFront;
import com.msu.evolving.FactoryThiefVariable;
import com.msu.moo.model.AProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class OptimalTourIsDominating extends AProblem<FactoryThiefVariable>{

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}
	

	@Override
	public int getNumberOfConstraints() {
		return 1;
	}


	@Override
	protected void evaluate_(FactoryThiefVariable var, List<Double> objectives, List<Double> constraintViolations) {
		NonDominatedSolutionSet set = new ThiefExhaustive().run(var.get());
		int numOfDifferentTour = new DifferentToursInFront().analyze(set);
		
		constraintViolations.add((double) numOfDifferentTour);
		objectives.add(-(double) set.size());
		
	}
	
	

	

}
