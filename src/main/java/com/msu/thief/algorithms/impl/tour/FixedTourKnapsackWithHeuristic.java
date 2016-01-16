package com.msu.thief.algorithms.impl.tour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.thief.algorithms.ItemHeuristicUtil;
import com.msu.thief.algorithms.impl.subproblems.AlgorithmUtil;
import com.msu.thief.algorithms.interfaces.IFixedTourAlgorithm;
import com.msu.thief.model.Item;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

/**
 * This algorithm is also based on a heuristic. In the beginning the gain of
 * each item when it is added to an empty knapsack is calculated. 
 * 
 * Based on the the single objective knapsack problem is solved where each item has the
 * profit equal to the initial gain. If the initial gain is negative (which
 * means a loss) the item has a value of 0.
 * 
 * calcItemGains -> createItemsWithGainAsProfit -> solveKnapsack
 *
 */
public class FixedTourKnapsackWithHeuristic implements IFixedTourAlgorithm  {

	/**
	 * If this options is set there will be a post greedy post pruning to optimize
	 * the objective value.
	 */
	protected boolean postPruneItems = true;
	
	


	@Override
	public Solution<Pack> run(ThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {
		
		// evaluate empty knapsack
		Pack pack = new Pack();
		double empty = eval.evaluate(problem, pack).getObjective(0);
		
		final int numOfItems = problem.getProblem().numOfItems();
		
		// calculate all objectives values when one item is added
		Collection<Integer> possibleNextItems = pack.getNotPickedItems(numOfItems);
		Map<Integer, Solution<Pack>> next = ItemHeuristicUtil.calcObjectiveWhenAdded(problem, eval, pack,
				possibleNextItems);

		
		// create a list of items with heuristic values as profit
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < numOfItems; i++) {
			
			double heuristic = empty - next.get(i).getObjective(0);
			
			// do eliminate if upper bound is lower than zero
			if (heuristic < 0) heuristic = 0;
			
			final double weight = problem.getProblem().getItems().get(i).getWeight();
			
			Item item = new Item(heuristic, weight);
			items.add(item);
		}
		
		// solve this problem optimally
		pack = AlgorithmUtil.calcBestPackingPlan(problem.getProblem().getItems(), problem.getProblem().getMaxWeight());
		
		Solution<Pack> best = eval.evaluate(problem,pack);
		
		
		if (postPruneItems) {
			
			while(pack.isAnyPicked()){
				
				// calculate all items when they are removed
				Map<Integer, Solution<Pack>> mRemove = ItemHeuristicUtil.calcObjectiveRemoved(problem, eval, pack);
				
				List<Pair<Integer, Solution<Pack>>> nextIndices = new ArrayList<>();
				for (Entry<Integer, Solution<Pack>> entry : mRemove.entrySet()) {
					if (new SolutionDominatorWithConstraints().isDominating(entry.getValue(), best)) {
						nextIndices.add(Pair.create(entry.getKey(), entry.getValue()));
					}
				}
				
				if (nextIndices.isEmpty()) break;
				
				final int idx = FixedTourGreedyPackingAlgorithm.selectBest(nextIndices);
				pack.remove(idx);
				best = eval.evaluate(problem,pack);
				
			}
			
		}
		
		return best;
	}

	


}
