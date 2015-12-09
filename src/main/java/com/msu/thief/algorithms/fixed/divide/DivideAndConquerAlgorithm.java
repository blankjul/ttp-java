package com.msu.thief.algorithms.fixed.divide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.SingleObjectiveThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

/**
 * Divide and Conquer principle starting with all the items.
 * 
 * [0,1,2,3,4,5,6,7]
 * [0,1,2,3] [4,5,6,7]
 * [0,1] [2,3] [4,5] [6,7]
 * [0] [1] [2] [3] [4] [5] [6] [7] [8] [9]
 * 
 * and then the merge process begins
 */
public class DivideAndConquerAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblemWithFixedTour> {

	protected List<Integer> shuffle;
	
	
	@Override
	public Solution run___(SingleObjectiveThiefProblemWithFixedTour problem, IEvaluator eval, MyRandom rand) {

		shuffle = new ArrayList<>();
		for (int i = 0; i < problem.numOfItems(); i++) {
			shuffle.add(i);
		}
		rand.shuffle(shuffle);
		
		
		Set<Integer> indices = solve(problem, eval, 0, problem.numOfItems() - 1, 0);

		//Set<Integer> indices = new HashSet<>();
		//DivideAndConquerUtil.packGreedy(problem, eval, tour, indices);
		
		Solution s = eval.evaluate(problem,new BooleanPackingList(indices, problem.numOfItems()));

		//DivideAndConquerUtil.reportFinalState(problem, eval, tour, indices);

		return s;
	}
	
	

	protected Set<Integer> solve(SingleObjectiveThiefProblemWithFixedTour problem, IEvaluator eval, int start,
			int end, int level) {


		if (start == end) {
			return new HashSet<>(Arrays.asList(shuffle.get(start)));
		}

		int middle = start + (end - start) / 2;
		Set<Integer> left = solve(problem, eval, start, middle, level +1);
		Set<Integer> right = solve(problem, eval, middle + 1, end, level + 1);
		
		Pair<Set<Integer>, Double> mergedA = merge(problem, eval, left, right);
		Pair<Set<Integer>, Double> mergedB = merge(problem, eval, right, left);
		
		Pair<Set<Integer>, Double> merged = (mergedA.second < mergedB.second) ? mergedA : mergedB;
		
		for (int i = 0; i < level; i++) {
			System.out.print("----");
		}
		System.out.println(String.format("%s", Arrays.toString(merged.first.toArray())));
		
		//DivideAndConquerUtil.pruneUntilNoImprovement(problem, eval, tour, merged);

		return merged.first;
	}

	
	protected Pair<Set<Integer>, Double> merge(SingleObjectiveThiefProblemWithFixedTour problem, IEvaluator eval, Set<Integer> left, Set<Integer> right) {
		Set<Integer> merged = new HashSet<>(left);
		double best = eval
				.evaluate(problem,new BooleanPackingList(merged, problem.numOfItems()))
				.getObjectives(0);

		for (Integer next : right) {
			merged.add(next);
			double fitnessMerged = eval
					.evaluate(problem, new BooleanPackingList(merged, problem.numOfItems()))
					.getObjectives(0);
			if (best <= fitnessMerged)
				merged.remove(next);
			else
				best = fitnessMerged;
		}
		return Pair.create(merged, best);
	}
	


}
