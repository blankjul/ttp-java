package com.msu.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.util.Random;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;

public class HillClimbingOnPacking extends AbstractAlgorithm {

	
	protected Tour<?> bestTour = null;
	protected PackingList<?> bestList = null;
	protected Solution best = null;
	
	NonDominatedSolutionSet set = null;
	
	@Override
	public NonDominatedSolutionSet run_(IEvaluator eval, Random rand) {

		set = new NonDominatedSolutionSet();

		ThiefProblem problem = (ThiefProblem) eval.getProblem();
		Tour<?> optimal = new SalesmanLinKernighanHeuristic().getTour(
				new Evaluator(new SalesmanProblem(problem.getMap())));
		Tour<?> symmetricOptimal = optimal.getSymmetric();
		
		checkAndSetIfBest(eval, optimal, new EmptyPackingListFactory().next(problem, rand));
		checkAndSetIfBest(eval, symmetricOptimal, new EmptyPackingListFactory().next(problem, rand));
		
		int counter = 0;
		
		while (eval.hasNext()) {
			
			if (counter == eval.getMaxEvaluations() / 10) {
				bestList = new EmptyPackingListFactory().next(problem, rand);
			}

			PackingList<?> nextList = (PackingList<?>) new BitFlipMutation().mutate(bestList.copy(), rand);
			boolean added = checkAndSetIfBest(eval, bestTour, nextList);
			boolean addedSymm = checkAndSetIfBest(eval, bestTour.getSymmetric(), nextList);
			
			if (!added && !addedSymm) {
				++counter;
			} else {
				counter = 0;
			}
			
		}

		return set;
	}
	
	
	
	
	
	protected boolean checkAndSetIfBest(IEvaluator eval, Tour<?> t, PackingList<?> b) {
		Solution s = eval.evaluate(new TTPVariable(t,b));
		if (set.add(s)) {
			bestTour = t;
			bestList = b;
			best = s;
			return true;
		}
		return false;
	}
	
	
	

}
