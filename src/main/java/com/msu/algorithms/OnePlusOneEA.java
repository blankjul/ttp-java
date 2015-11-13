package com.msu.algorithms;

import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.AbstractAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.SingleObjectiveThiefProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;
import com.msu.util.Random;

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
	public NonDominatedSolutionSet run_(IProblem p, IEvaluator eval, Random rand) {

		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		ThiefProblem problem =(ThiefProblem) p;
		if (solveSingleObjective) ((SingleObjectiveThiefProblem) problem).setToMultiObjective(false);
		
		SymmetricMap map = problem.getMap();
		map.multipleCosts(problem.getMaxSpeed());
		
		SalesmanProblem tsp = new SalesmanProblem(map);
		
		Tour<?> bestTour = new SalesmanLinKernighanHeuristic().getTour(tsp, new Evaluator(Integer.MAX_VALUE));
		Tour<?> symmetricBest = bestTour.getSymmetric();
		
		PackingList<?> bestList = new EmptyPackingListFactory().next(problem, rand);

		while (eval.hasNext()) {
			
			List<Boolean> nextList = ((PackingList<?>) new BitFlipMutation().mutate(bestList.copy(), rand)).encode();
			Pair<Tour<?>, PackingList<?>> pair = Pair.create(bestTour, new BooleanPackingList(nextList));
			
			// check if this solution is better
			Solution s = eval.evaluate(problem, new TTPVariable(pair));
			boolean isOptimal = set.add(s);

			if (isOptimal)
				bestList = new BooleanPackingList(nextList);
			
			// check also for the symmetric solution
			Solution s2 = eval.evaluate(problem, new TTPVariable(Pair.create(symmetricBest, new BooleanPackingList(nextList))));
			isOptimal = set.add(s2);

			if (isOptimal)
				bestList = new BooleanPackingList(nextList);
			
		}
		
		if (!solveSingleObjective) {
			return set;
		} else {
			if (solveSingleObjective) ((SingleObjectiveThiefProblem) problem).setToMultiObjective(true);
			NonDominatedSolutionSet result = new NonDominatedSolutionSet();
			Solution best = eval.evaluate(problem, set.get(0).getVariable());
			result.add(best);
			return result;
		}
		
	}

}
