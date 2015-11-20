package com.msu.thief.algorithms;

import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractDomainAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.mutation.BitFlipMutation;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;
import com.msu.util.MyRandom;

public class OnePlusOneEA extends AbstractDomainAlgorithm<SingleObjectiveThiefProblem> {

	protected boolean returnMultiObjectiveResult = true;
	public boolean checkSymmetric = false;
	
	
	public OnePlusOneEA() {
		super();
	}
	
	public OnePlusOneEA(boolean solveSingleObjective) {
		super();
		this.returnMultiObjectiveResult = solveSingleObjective;
	}


	@Override
	public NonDominatedSolutionSet run__(SingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {


		boolean original = problem.isSwtichedToMultiObjective();
		problem.setToMultiObjective(false);
		
		Tour<?> bestTour = AlgorithmUtil.calcBestTour(problem);
		PackingList<?> bestList = new EmptyPackingListFactory().next(problem, rand);
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		while (eval.hasNext()) {
			
			List<Boolean> nextList = ((PackingList<?>) new BitFlipMutation().mutate(bestList.copy(), problem, rand)).encode();
			Pair<Tour<?>, PackingList<?>> pair = Pair.create(bestTour, new BooleanPackingList(nextList));
			
			// check if this solution is better
			Solution s = eval.evaluate(problem, new TTPVariable(pair));
			boolean isOptimal = set.add(s);

			if (isOptimal)
				bestList = new BooleanPackingList(nextList);
			
			// check also for the symmetric solution
			if (checkSymmetric) {
				Solution s2 = eval.evaluate(problem, new TTPVariable(Pair.create(bestTour.getSymmetric(), new BooleanPackingList(nextList))));
				isOptimal = set.add(s2);
				if (isOptimal) {
					bestList = new BooleanPackingList(nextList);
					bestTour = bestTour.getSymmetric();
				}
			}
			
		}
		
		
		if (returnMultiObjectiveResult) {
			problem.setToMultiObjective(true);
			NonDominatedSolutionSet result = new NonDominatedSolutionSet();
			Solution best = eval.evaluate(problem, set.get(0).getVariable());
			result.add(best);
			set = result;
		}
		
		problem.setToMultiObjective(original);
		
		return set;
		
	}

}
