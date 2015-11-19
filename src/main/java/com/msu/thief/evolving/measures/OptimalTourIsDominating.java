package com.msu.thief.evolving.measures;

import java.util.List;

import com.msu.model.AProblem;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.algorithms.exhaustive.ThiefExhaustive;
import com.msu.thief.analyze.ThiefAmountOfDifferentTours;
import com.msu.thief.evolving.ThiefProblemVariable;
import com.msu.util.Random;

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
		NonDominatedSolutionSet set = new ThiefExhaustive().run(var.get(), new Evaluator(Integer.MAX_VALUE), new Random());
		int numOfDifferentTour = new ThiefAmountOfDifferentTours().analyze(set);
		
		constraintViolations.add((double) numOfDifferentTour - 2);
		objectives.add(-(double) set.size());
		System.out.println(++counter);
	}
	
	

	

}
