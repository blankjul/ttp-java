package com.msu.thief.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.ASingleObjectiveAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.util.MyRandom;
import com.msu.thief.algorithms.subproblems.AlgorithmUtil;
import com.msu.thief.ea.factory.PackFullFactory;
import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefOnePlusOneEA extends ASingleObjectiveAlgorithm<TTPVariable, SingleObjectiveThiefProblem>{

	
	// when is the starting point reseted
	protected int numOfNoImprovements = 1000;

	
	@Override
	public Solution<TTPVariable> run(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {
		
		int counter = 0;
		Solution<TTPVariable> current = calcStartingSolution(problem, evaluator, rand);
		Solution<TTPVariable> best = current;
				
		while (evaluator.hasNext()) {
			
			// reset the starting point when no improvement for N iterations
			if (counter > numOfNoImprovements) {
				
				// save current if this run found a new best
				if (SolutionDominator.isDominating(current, best)) best = current;
				
				// reset everything
				current = calcStartingSolution(problem, evaluator, rand);
				counter = 0;
			} 
			
			// create next variable by doing a bitflip mutation - at least one flip
			Pack nextPack = current.getVariable().getPack().copy();
			new PackBitflipMutation(problem).mutate(nextPack, rand);
			Solution<TTPVariable> next = evaluator.evaluate(problem, TTPVariable.create(current.getVariable().getTour(), nextPack));
			
			// if hill climbing was successfully reset counter to 0
			if (SolutionDominator.isDominating(next, current)) {
				current = next;
				counter = 0;
			}
			// else increase non improved counter
			else counter++;
			
		}

		return best;
	}
	
	public static Solution<TTPVariable> calcStartingSolution(SingleObjectiveThiefProblem problem, IEvaluator evaluator, MyRandom rand) {
		
		// random tour
		Tour tour = AlgorithmUtil.calcBestTour(problem);
		if (rand.nextDouble() < 0.5) tour = tour.getSymmetric();
			
		// full packing plan
		Pack z = new PackFullFactory(problem).next(rand);
		//Pack z = new Pack();
		
		return evaluator.evaluate(problem, TTPVariable.create(tour, z));
	}

}
