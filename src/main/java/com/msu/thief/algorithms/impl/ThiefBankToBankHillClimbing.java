package com.msu.thief.algorithms.impl;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.evaluator.ConvergenceEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.impl.bilevel.pack.FixedPackSwapTour;
import com.msu.thief.algorithms.impl.bilevel.tour.FixedTourOnePlusOneEA;
import com.msu.thief.algorithms.interfaces.AThiefSingleObjectiveAlgorithm;
import com.msu.thief.ea.factory.TourOptimalFactory;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedPack;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefBankToBankHillClimbing extends AThiefSingleObjectiveAlgorithm {

	
	protected Solution<Pack> optPack(SingleObjectiveThiefProblem thief, IEvaluator eval, MyRandom rand, Tour t, Pack p) {
		ThiefProblemWithFixedTour tourProblem = new ThiefProblemWithFixedTour(thief, t);
		IEvaluator local = new ConvergenceEvaluator(100);
		local.setFather(eval);
		Solution<Pack> nextPack = new FixedTourOnePlusOneEA(p).run(tourProblem, local, rand);
		return nextPack;
	}
	
	protected Solution<Tour> optTour(SingleObjectiveThiefProblem thief, IEvaluator eval, MyRandom rand, Tour t, Pack p) {
		ThiefProblemWithFixedPack packProblem = new ThiefProblemWithFixedPack(thief, p);
		return new FixedPackSwapTour(t).run(packProblem, eval, rand);
	}
	
	
	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem thief, IEvaluator evaluator, MyRandom rand) {


		// factory for the starting point
		TTPVariable var = TTPVariable.create(new TourOptimalFactory(thief).next(rand), Pack.empty());
		Solution<TTPVariable> currentBest = thief.evaluate(var);
		

		while (evaluator.hasNext()) {

			// now take the tour and optimize packing plan
			Solution<Pack> nextPack = optPack(thief, evaluator, rand, currentBest.getVariable().getTour(), currentBest.getVariable().getPack());
			
			Solution<Tour> nextTour = optTour(thief, evaluator, rand, currentBest.getVariable().getTour(), nextPack.getVariable());
			
			TTPVariable next = TTPVariable.create(nextTour.getVariable(), nextPack.getVariable());
			Solution<TTPVariable> s = thief.evaluate(next);
			
			//System.out.println(nextPack);
			//System.out.println(currentBest);
			//System.out.println("----------------");
			
			// update the best found solution
			if (SolutionDominator.isDominating(s, currentBest)) {
				currentBest = s;
				//System.out.println("YES");
			}
			evaluator.notify(currentBest);

		}

		return currentBest;
	}

}
