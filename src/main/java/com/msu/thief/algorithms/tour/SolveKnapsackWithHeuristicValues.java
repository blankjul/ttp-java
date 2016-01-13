package com.msu.thief.algorithms.tour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.thief.algorithms.AFixedTourAlgorithm;
import com.msu.thief.algorithms.ItemHeuristicUtil;
import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.model.Item;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
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
public class SolveKnapsackWithHeuristicValues extends AFixedTourAlgorithm  {

	/**
	 * If this options is set there will be a post greedy post pruning to optimize
	 * the objective value.
	 */
	protected boolean postPruneItems = true;
	
	


	@Override
	public Solution run_(FixedTourSingleObjectiveThiefProblem thief, IEvaluator eval, MyRandom rand) {
		
		// evaluate empty knapsack
		Pack pack = new Pack();
		double empty = eval.evaluate(thief, pack).getObjective(0);
		
		final int numOfItems = thief.getProblem().numOfItems();
		
		// calculate all objectives values when one item is added
		Collection<Integer> possibleNextItems = pack.getNotPickedItems(numOfItems);
		Map<Integer, Solution> next = ItemHeuristicUtil.calcObjectiveWhenAdded(thief, eval, pack,
				possibleNextItems);

		
		// create a list of items with heuristic values as profit
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < numOfItems; i++) {
			
			double heuristic = empty - next.get(i).getObjective(0);
			
			// do eliminate if upper bound is lower than zero
			if (heuristic < 0) heuristic = 0;
			
			final double weight = thief.getProblem().getItems().get(i).getWeight();
			
			Item item = new Item(heuristic, weight);
			items.add(item);
		}
		
		// solve this problem optimally
		pack = AlgorithmUtil.calcBestPackingPlan(thief.getProblem().getItems(), thief.getProblem().getMaxWeight());
		Solution best = eval.evaluate(thief.getProblem(),TTPVariable.create(thief.getTour(), pack));
		
		
		if (postPruneItems) {
			
			while(pack.isAnyPicked()){
				
				// calculate all items when they are removed
				Map<Integer, Solution> mRemove = ItemHeuristicUtil.calcObjectiveRemoved(thief, eval, pack);
				
				List<Pair<Integer, Solution>> nextIndices = new ArrayList<>();
				for (Entry<Integer, Solution> entry : mRemove.entrySet()) {
					if (new SolutionDominatorWithConstraints().isDominating(entry.getValue(), best)) {
						nextIndices.add(Pair.create(entry.getKey(), entry.getValue()));
					}
				}
				
				if (nextIndices.isEmpty()) break;
				
				final int idx = GreedyPackingAlgorithm.selectBest(nextIndices);
				pack.remove(idx);
				best = eval.evaluate(thief.getProblem(),TTPVariable.create(thief.getTour(), pack));
				
			}
			
		}
		
		return best;
	}

	


}
