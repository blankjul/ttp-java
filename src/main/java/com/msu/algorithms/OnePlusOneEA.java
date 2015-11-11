package com.msu.algorithms;

import java.util.List;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.mutation.BitFlipMutation;
import com.msu.moo.util.Pair;
import com.msu.moo.util.Random;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.SingleObjectiveThiefProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;

public class OnePlusOneEA extends AbstractAlgorithm {

	protected boolean solveSingleObjective = true;
	
	
	public OnePlusOneEA() {
		super();
	}
	

	public OnePlusOneEA(boolean solveSingleObjective) {
		super();
		this.solveSingleObjective = solveSingleObjective;
		this.name += "-MultiObjective";
	}


	@Override
	public NonDominatedSolutionSet run_(IEvaluator eval, Random rand) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		ThiefProblem problem =(ThiefProblem) eval.getProblem();
		if (solveSingleObjective) ((SingleObjectiveThiefProblem) problem).setToMultiObjective(false);
		
		SymmetricMap map = problem.getMap();
		map.multipleCosts(problem.getMaxSpeed());
		
		SalesmanProblem tsp = new SalesmanProblem(map);
		
		Tour<?> bestTour = new SalesmanLinKernighanHeuristic().getTour(new Evaluator(tsp));
		Tour<?> symmetricBest = bestTour.getSymmetric();
		
		PackingList<?> bestList = new EmptyPackingListFactory().next(problem, rand);

		while (eval.hasNext()) {

	/*		List<Boolean> nextList = new ArrayList<>(bestList.get());
			for (int i = 0; i < nextList.size(); i++) {
				if (rand.nextDouble() < 1d / nextList.size()) {
					nextList.set(i, !nextList.get(i));
				}
			}*/
			
			List<Boolean> nextList = ((PackingList<?>) new BitFlipMutation().mutate(bestList.copy(), rand)).encode();
			Pair<Tour<?>, PackingList<?>> p = Pair.create(bestTour, new BooleanPackingList(nextList));
			
			// check if this solution is better
			Solution s = eval.evaluate(new TTPVariable(p));
			boolean isOptimal = set.add(s);

			if (isOptimal)
				bestList = new BooleanPackingList(nextList);
			
			// check also for the symmetric solution
			Solution s2 = eval.evaluate(new TTPVariable(Pair.create(symmetricBest, new BooleanPackingList(nextList))));
			isOptimal = set.add(s2);

			if (isOptimal)
				bestList = new BooleanPackingList(nextList);
			
		}
		
		if (!solveSingleObjective) {
			return set;
		} else {
			if (solveSingleObjective) ((SingleObjectiveThiefProblem) problem).setToMultiObjective(true);
			NonDominatedSolutionSet result = new NonDominatedSolutionSet();
			Solution best = eval.evaluate(set.get(0).getVariable());
			result.add(best);
			return result;
		}
		
	}

}
