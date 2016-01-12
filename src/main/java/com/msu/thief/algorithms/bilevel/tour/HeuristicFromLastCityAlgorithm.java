package com.msu.thief.algorithms.bilevel.tour;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.util.MyRandom;

/**
 * Greedy algorithms starts with an empty knapsack and starting to add items
 * according to a specific heuristic.
 * 
 * TYPE.BEST Always an item with the largest improvement (and feasible) is taken
 * -> deterministic
 * 
 * TYPE.RANDOM Take randomly one item which brings an improvement (and
 * feasible).
 * 
 */
public class HeuristicFromLastCityAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour> {

	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {

		// empty packing list
		Solution best =  eval.evaluate(problem, new IntegerSetPackingList(problem.numOfItems()));

		// start from the last city of the fixed tour problem
		for (int city = problem.numOfCities() - 1; city >= 0; city--) {

			for (Integer item : problem.getItemCollection().getItemsFromCityByIndex(city)) {

				IntegerSetPackingList pack = (IntegerSetPackingList) best.getVariable();
				pack.get().first.add(item);
				
				Solution next = eval.evaluate(problem, pack);
				
				if (new SolutionDominatorWithConstraints().isDominating(next, best)) {
					best = next;
				} else {
					pack.get().first.remove(item);
				}
				

			}
		}
		
		return best;
	}
	
	
}
