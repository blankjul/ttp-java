package com.msu.thief.util;

import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.operators.AbstractMutation;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.TwoOptFactory;
import com.msu.util.MyRandom;

public class TwoOptMutation extends AbstractMutation<List<Integer>> {

	
	public IVariable mutate(IVariable a, IProblem problem, MyRandom rand) {
		
		SalesmanProblem tsp = null;
		if (problem instanceof SalesmanProblem) tsp = (SalesmanProblem) problem;
		else if (problem instanceof AbstractThiefProblem) tsp = new SalesmanProblem(((AbstractThiefProblem) problem).getMap());
		else throw new RuntimeException("Specific crossover needs salesman or thief problem.");
		
		return TwoOptFactory.optimize2Opt(tsp, (Tour<?>) a, rand, true);
	}
	

	@Override
	protected List<Integer> mutate_(List<Integer> element, IProblem problem, MyRandom rand) {
		return null;
	}
}
