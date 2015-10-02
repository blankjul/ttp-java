package com.msu.algorithms;

import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public abstract class AExhaustiveAlgorithm extends AbstractAlgorithm{

	protected boolean onlyNonDominatedPoints = true;
	
	protected class ExhaustiveSolutionSet extends NonDominatedSolutionSet {
		@Override
		public boolean add(Solution solutionToAdd) {
			return solutions.add(solutionToAdd);
		}
	}
	
	public AExhaustiveAlgorithm setOnlyNonDominatedPoints(boolean onlyNonDominatedPoints) {
		this.onlyNonDominatedPoints = onlyNonDominatedPoints;
		return this;
	}
	
}
