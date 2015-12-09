package com.msu.thief.algorithms.fixed.apriori;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.SingleObjectiveThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.util.MyRandom;

public class AprioriAlgorithm  extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblemWithFixedTour> {

	// final public int NUM_OF_POPULATION = 1000;

	// ! evaluator for this problem. set for every run
	protected IEvaluator eval;

	// ! problem for this run
	protected SingleObjectiveThiefProblemWithFixedTour problem;

	// ! random generator
	protected MyRandom rand;

	// ! set for this run
	NonDominatedSolutionSet set;

	@Override
	public Solution run___(SingleObjectiveThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {

		// initialize the variables
		this.problem = problem;
		this.eval = eval;
		this.rand = rand;
		this.set = new NonDominatedSolutionSet();


		// all the nodes to work with
		MutableList<AprioriNode> nodes = new FastList<>(Arrays.asList(createRootNode()));

		
		// while there are nodes land evaluations
		report(nodes);
		while (eval.hasNext() && !nodes.isEmpty()) {
			nodes = next(nodes);
			report(nodes);
		}

		return set.get(0);
	}

	protected void report(MutableList<AprioriNode> nodes) {
		List<AprioriEntry> entries = nodes.flatCollect(AprioriNode::getChildren);
		Collections.sort(entries,
				(o1, o2) -> Double.compare(o1.solution.getObjectives(0), o2.solution.getObjectives(0)));

		for (AprioriEntry e : entries.subList(0, Math.min(10, entries.size()))) {
			System.out.println(String.format("%s -> %s", e.solution.getObjectives(0), Arrays.toString(e.items.toArray())));
		}
		System.out.println(entries.size());
		System.out.println("---------------------------");

	}
	
	protected MutableList<AprioriNode> next(MutableList<AprioriNode> nodes) {
		MutableList<AprioriNode> next = new FastList<>();
		for (AprioriNode node : nodes) {
			if (!eval.hasNext()) break;
			next.addAll(node.expand(eval, problem, set));
		}
		return next;
	}

	
	
	protected AprioriNode createRootNode() {
		// solution without picking any item
		Solution empty = eval.evaluate(problem, new EmptyPackingListFactory().next(problem, rand));
		set.add(empty);

		// create the root apriori node
		AprioriNode root = new AprioriNode();
		for (int i = 0; i < problem.numOfItems(); i++) {
			AprioriEntry entry = new AprioriEntry(i, new HashSet<>(Arrays.asList(i)));
			entry.evaluate(eval, problem);

			// add only if node is better than father
			if (empty.getObjectives(0) > entry.solution.getObjectives(0))
				root.add(entry);
		}
		return root;
	}




}
