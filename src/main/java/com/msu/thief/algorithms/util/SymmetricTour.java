package com.msu.thief.algorithms.util;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.algorithms.nsgaII.INSGAIIModifactor;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.thief.variable.TTPVariable;
import com.msu.util.Pair;
import com.msu.util.MyRandom;

public class SymmetricTour implements INSGAIIModifactor {
	
	
	@Override
	public void modify(IProblem p, IEvaluator eval, SolutionSet population, MyRandom rand) {
		
		NonDominatedSolutionSet front = new NonDominatedSolutionSet(population);
		Solution s = front.get(rand.nextInt(front.size()));
		TTPVariable var = (TTPVariable) s.getVariable().copy();
		var.set(Pair.create(var.getTour().getSymmetric(), var.getPackingList()));		
		population.add(eval.evaluate(p, var));
	}
	
}