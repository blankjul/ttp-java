package com.msu.algorithms;

import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public abstract class AExhaustiveAlgorithm extends AbstractAlgorithm {

	protected boolean onlyNonDominatedPoints = true;

	protected class ExhaustiveSolutionSet extends NonDominatedSolutionSet {
		@Override
		public boolean add(Solution solutionToAdd) {

			for (Solution s : solutions) {

				// if the solution objectives are exactly equal
				if (cmp.isEqual(s, solutionToAdd)) {
					// if variable are also equal -> don't add it
					if ((s.getVariable() != null && solutionToAdd.getVariable() != null) && 
							s.getVariable().equals(solutionToAdd.getVariable())) {
						return false;
					}
				}
			}

			return solutions.add(solutionToAdd);
		}
	}

	public AExhaustiveAlgorithm setOnlyNonDominatedPoints(boolean onlyNonDominatedPoints) {
		this.onlyNonDominatedPoints = onlyNonDominatedPoints;
		return this;
	}

}
