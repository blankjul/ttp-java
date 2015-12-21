package com.msu.thief.algorithms.bilevel.tour.divide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.thief.algorithms.AlgorithmSingleObjectiveUtil;
import com.msu.thief.algorithms.bilevel.tour.GreedyPackingAlgorithm;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

/**
 * Divide and Conquer principle starting with all the items.
 * 
 * [0,1,2,3,4,5,6,7] [0,1,2,3] [4,5,6,7] [0,1] [2,3] [4,5] [6,7] [0] [1] [2] [3]
 * [4] [5] [6] [7] [8] [9]
 * 
 * and then the merge process begins
 */
public class DivideAndConquerAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour> {

	protected List<Integer> shuffle;

	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {

		shuffle = new ArrayList<>();
		for (int i = 0; i < problem.numOfItems(); i++) {
			shuffle.add(i);
		}
		rand.shuffle(shuffle);

		Set<Integer> indices = solve(problem, eval, 0, problem.numOfItems() - 1, 0);
		Solution s = eval.evaluate(problem, new BooleanPackingList(indices, problem.numOfItems()));

		return s;
	}

	protected Set<Integer> solve(ThiefProblemWithFixedTour problem, IEvaluator eval, int start, int end, int level) {

		if (start == end) {
			return new HashSet<>(Arrays.asList(shuffle.get(start)));
		}

		int middle = start + (end - start) / 2;
		Set<Integer> left = solve(problem, eval, start, middle, level + 1);
		Set<Integer> right = solve(problem, eval, middle + 1, end, level + 1);

		Pair<Set<Integer>, Solution> mergedA = merge(problem, eval, left, right);
		Pair<Set<Integer>, Solution> mergedB = merge(problem, eval, right, left);

		Pair<Set<Integer>, Solution> merged = (new SolutionDominatorWithConstraints().isDominating(mergedA.second, mergedB.second)) 
				? mergedA : mergedB;

		/*
		 * for (int i = 0; i < level; i++) { System.out.print("----"); }
		 * System.out.println(String.format("%s",
		 * Arrays.toString(merged.first.toArray())));
		 */

		return merged.first;
	}

	protected Pair<Set<Integer>, Solution> merge(ThiefProblemWithFixedTour problem, IEvaluator eval, Set<Integer> left,
			Set<Integer> right) {

		Set<Integer> remaining = new HashSet<>(right);
		IntegerSetPackingList pack = new IntegerSetPackingList(new HashSet<>(left), problem.numOfItems());
		Solution best = eval.evaluate(problem, pack);

		while (!remaining.isEmpty()) {

			List<Pair<Integer, Solution>> nextIndices = new ArrayList<>();
			Map<Integer, Solution> next = AlgorithmSingleObjectiveUtil.calcObjectiveWhenAdded(problem, eval, pack,
					remaining);
			for (Entry<Integer, Solution> entry : next.entrySet()) {
				if (new SolutionDominatorWithConstraints().isDominating(entry.getValue(), best)) {
					nextIndices.add(Pair.create(entry.getKey(), entry.getValue()));
				}
			}
			
			// no profitable items found
			if (nextIndices.isEmpty()) break;
			
			// add the best item - remove remaining - add to pack
			final int packIdx = GreedyPackingAlgorithm.selectBest(nextIndices);
			remaining.remove(packIdx);
			pack.get().first.add(packIdx);
			best = next.get(packIdx);

		}

		return Pair.create(pack.toIndexSet(), best);
	}

}
