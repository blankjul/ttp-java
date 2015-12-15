package com.msu.thief.algorithms.bilevel.tour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.util.Combination;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.util.MyRandom;
import com.msu.util.Util;

/**
 * Exhaustively searches for all item combination which are possible.
 * If itemsToConsider is set only a specific amount of items is considered.
 */
public class ExhaustiveItemSetSolver extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour> {

	//! all items that should be considered
	protected Set<Integer> itemsToConsider;
	
	public ExhaustiveItemSetSolver() {
		super();
		
	}

	public ExhaustiveItemSetSolver(Set<Integer> itemsToConsider) {
		super();
		this.itemsToConsider = itemsToConsider;
	}


	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator evaluator, MyRandom rand) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		
		if (itemsToConsider == null) itemsToConsider = new HashSet<>(Util.createIndex(problem.numOfItems()));
		
		for (int i = 0; i <= itemsToConsider.size(); i++) {
			Combination combination = new Combination(new ArrayList<>(itemsToConsider), i);
			while (combination.hasNext()) {
				Solution s = evaluator.evaluate(problem,
						new IntegerSetPackingList(new HashSet<>(combination.next()), problem.numOfItems()));
				set.add(s);
			}
		}
		return set.get(0);
	}

}
