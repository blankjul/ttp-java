package com.msu.algorithms.util;

import com.msu.moo.algorithms.nsgaII.INSGAIIModifactor;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Random;
import com.msu.problems.SalesmanProblem;
import com.msu.problems.ThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.TwoOptFactory;

public class TwoOptLocalSearch implements INSGAIIModifactor {
	
	@Override
	public void modify(IEvaluator eval, SolutionSet population, Random rand) {
		Solution s = population.get(rand.nextInt(population.size()));
		TTPVariable var = (TTPVariable) s.getVariable();

		ThiefProblem thiefProblem = (ThiefProblem) eval.getProblem();
		SalesmanProblem salesmanProblem = new SalesmanProblem(thiefProblem.getMap());
		Tour<?> t = TwoOptFactory.optimize2Opt(salesmanProblem, var.getTour(), rand);

		Solution localOpt = eval.evaluate(new TTPVariable(t, (PackingList<?>) var.getPackingList().copy()));
		population.add(localOpt);
	}
	
}