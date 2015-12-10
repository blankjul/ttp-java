package com.msu.thief.algorithms.bilevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmSingleObjectiveUtil;
import com.msu.thief.algorithms.KnapsackCombo;
import com.msu.thief.model.Item;
import com.msu.thief.problems.KnapsackProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.util.MyRandom;

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
public class SolveKnapsackWithEmptyHeuristicValues
		extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour> {

	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {

		// empty packing list
		IntegerSetPackingList pack = new IntegerSetPackingList(problem.numOfItems());

		double empty = eval.evaluate(problem, pack).getObjectives(0);

		// calculate all objectives values when one item is added
		Map<Integer, Solution> next = AlgorithmSingleObjectiveUtil.calcObjectiveWhenAdded(problem, eval, pack,
				pack.getNotPickedItems());

		// create a list of items with heuristic values as profit
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < problem.numOfItems(); i++) {
			double heuristic = empty - next.get(i).getObjectives(0);
			if (heuristic < 0)
				heuristic = 0;
			Item item = new Item(heuristic, problem.getItems().get(i).getWeight());
			items.add(item);
		}

		// solve this problem optimally
		KnapsackProblem knp = new KnapsackProblem(problem.getMaxWeight(), items);
		Solution solKnp = new KnapsackCombo().run(knp, eval, rand).get(0);
		PackingList<?> b = ((PackingList<?>) solKnp.getVariable());
		
		return eval.evaluate(problem,b);
	}

}
