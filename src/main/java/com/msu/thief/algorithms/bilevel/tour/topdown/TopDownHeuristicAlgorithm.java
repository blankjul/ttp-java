package com.msu.thief.algorithms.bilevel.tour.topdown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

/**
 * 
 * Similar to the A* idea with a priority queue which always contains the most
 * promising node according to the single objective value. This node is then
 * expanded for all possible children.
 * 
 * All visited nodes are hash to get a reference to calculated subtrees.
 * 
 *
 */
public class TopDownHeuristicAlgorithm
		extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedTour> {

	protected Set<HeuristicNode> visited = new HashSet<>();

	final int NUM_OF_NEIGHBOURS = 7;

	@Override
	public Solution run___(ThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		Set<Integer> allIndices = new HashSet<>();
		for (int i = 0; i < problem.numOfItems(); i++) {
			allIndices.add(i);
		}
		HeuristicNode root = new HeuristicNode(allIndices);
		root.evaluate(eval, problem);
		HeuristicTree tree = new HeuristicTree(root);

		// create the open list sorted by current best value
		Queue<HeuristicNode> open = new PriorityQueue<>(new Comparator<HeuristicNode>() {
			@Override
			public int compare(HeuristicNode n1, HeuristicNode n2) {
				return Double.compare(n1.getFitness(), n2.getFitness());
			}
		});
		open.add(tree.getRoot());
		visited.add(tree.getRoot());

		while (eval.hasNext() && !open.isEmpty()) {

			HeuristicNode n = open.poll();
			List<Pair<Integer, Double>> nextIndices = new ArrayList<>();

			Set<HeuristicNode> children = n.expand();

			for (HeuristicNode child : children) {

				child.evaluate(eval, problem);

				if (!child.getSolution().hasConstrainViolations() && child.isBetterThanFather()
						&& !visited.contains(child)) {
					// if we got worse by picking that item
					open.add(child);
					visited.add(child);
					set.add(child.getSolution());
					nextIndices.add(Pair.create(child.idx, child.getFitness() - n.getFitness()));
					// System.out.println(Arrays.toString(child.currentIndices.toArray()));
				}
			}

			Collections.sort(nextIndices, Comparator.comparing(p -> p.second));

			for (HeuristicNode child : children) {
				Set<Integer> next = new HashSet<>();
				final int numOfNext = Math.min(nextIndices.size(), NUM_OF_NEIGHBOURS);
				for (int i = 0; i < numOfNext; i++) {
					if (nextIndices.get(i).second < 0)
						next.add(nextIndices.get(i).first);
				}
				child.setNextIndices(next);
				System.out.println(String.format("%s %s -> %s", Arrays.toString(child.currentIndices.toArray()),
						child.solution.getObjectives(0), Arrays.toString(next.toArray())));
			}

			// System.out.println(open.size());
			// System.out.println(Arrays.toString(new
			// BooleanPackingList(n.b).toIndexList().toArray()));

		}
		return set.get(0);
	}

}
