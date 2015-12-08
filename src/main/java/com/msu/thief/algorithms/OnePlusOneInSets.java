package com.msu.thief.algorithms;

import java.util.Arrays;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class OnePlusOneInSets extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	// ! problem for this run
	protected SingleObjectiveThiefProblem problem;

	protected IEvaluator eval;
	
	// ! random generator
	protected MyRandom rand;

	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {

		this.problem = problem;
		this.rand = rand;
		this.eval = eval;
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		Tour<?> tour = AlgorithmUtil.calcBestTour(problem).getSymmetric();
		Tour<?> symmetricTour = tour.getSymmetric();

		Solution current1 = eval.evaluate(problem, new TTPVariable(tour, new EmptyPackingListFactory().next(problem, rand)));
		Solution current2 = eval.evaluate(problem, new TTPVariable(symmetricTour, new EmptyPackingListFactory().next(problem, rand)));
		
		while (eval.hasNext()) {
			current1 = solve(current1, set);
			current2  = solve(current2, set);
		}

		return set.get(0);
	}

	
	private Solution solve(Solution s, NonDominatedSolutionSet set) {

		Set<Integer> items = ((TTPVariable) s.getVariable()).getPackingList().toIndexSet();
		
		double prob = 1 / (double) problem.numOfItems();
		
		for (int i = 0; i < problem.numOfItems(); i++) {
			if (rand.nextDouble() < prob) {
				if (items.contains(i)) items.remove(i);
				else items.add(i);
			}
		}
		Tour<?> tour = ((TTPVariable) s.getVariable()).getTour();
		Solution result = eval.evaluate(problem, new TTPVariable(tour, new BooleanPackingList(items, problem.numOfItems())));
		set.add(result);
		return (result.getObjectives(0) < s.getObjectives(0)) ? result : s;

	}


}
