package com.msu.evolving.measures;

import java.util.List;

import com.msu.analyze.ThiefAmountOfOptimalTourInFront;
import com.msu.evolving.ThiefProblemVariable;
import com.msu.moo.model.AProblem;

public class VariatyInTheFront extends AProblem<ThiefProblemVariable>{

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}
	

	@Override
	protected void evaluate_(ThiefProblemVariable var, List<Double> objectives, List<Double> constraintViolations) {
		//NonDominatedSolutionSet set = new ThiefExhaustive().run();
		//objectives.add(-(double) new ThiefAmountOfDifferentTours().analyze(set));
		objectives.add((double) new ThiefAmountOfOptimalTourInFront().analyze(var.get()));
		
	}
	
	

	

}
