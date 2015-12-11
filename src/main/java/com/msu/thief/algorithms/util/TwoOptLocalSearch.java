package com.msu.thief.algorithms.util;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.algorithms.nsgaII.INSGAIIModifactor;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.TwoOptFactory;
import com.msu.util.MyRandom;

public class TwoOptLocalSearch implements INSGAIIModifactor {
	
	@Override
	public void modify(IProblem p, IEvaluator eval, SolutionSet population, MyRandom rand) {
		Solution s = population.get(rand.nextInt(population.size()));
		TTPVariable var = (TTPVariable) s.getVariable();

		AbstractThiefProblem thiefProblem = (AbstractThiefProblem) p;
		SalesmanProblem salesmanProblem = new SalesmanProblem(thiefProblem.getMap());
		Tour<?> t = TwoOptFactory.optimize2Opt(salesmanProblem, var.getTour(), rand);

		Solution localOpt = eval.evaluate(p, new TTPVariable(t, (PackingList<?>) var.getPackingList().copy()));
		population.add(localOpt);
	}
	
}