package com.msu.thief.variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.operators.AbstractCrossover;
import com.msu.util.Pair;
import com.msu.util.MyRandom;

public class TTPCrossover extends AbstractCrossover<Pair<IVariable,IVariable>>{

	//! crossover for the tour
	protected AbstractCrossover<?> cTour;
	
	//! crossover for the packing plan
	protected AbstractCrossover<?> cPackingPlan;

	public TTPCrossover(AbstractCrossover<?> cTour, AbstractCrossover<?> cPackingPlan) {
		super();
		this.cTour = cTour;
		this.cPackingPlan = cPackingPlan;
	}
	
	
	@Override
	public List<Pair<IVariable,IVariable>> crossover_(Pair<IVariable,IVariable> a, Pair<IVariable,IVariable> b, IProblem problem,  MyRandom rand, IEvaluator eval) {
		
		List<IVariable> offTours = null;
		List<IVariable> offPlans = null;
		
		final double r = rand.nextDouble();
		
		// mutate only tour
		if (r < 0.33) {
			offTours = cTour.crossover(a.first, b.first, problem,rand);
			offPlans = Arrays.asList(a.second.copy(), b.second.copy());
		// mutate only pack
		} else if (r < 0.66) {
			offTours = Arrays.asList(a.first.copy(), b.first.copy());
			offPlans = cPackingPlan.crossover(a.second, b.second, problem, rand);
		// mutate both
		} else {
			offTours = cTour.crossover(a.first, b.first, problem,rand);
			offPlans = cPackingPlan.crossover(a.second, b.second, problem, rand);
		}
		
		Pair<IVariable,IVariable> p1 = Pair.create(offTours.get(0), offPlans.get(0));
		Pair<IVariable,IVariable> p2 = Pair.create(offTours.get(1), offPlans.get(1));
		
		return new ArrayList<Pair<IVariable,IVariable>>(Arrays.asList(p1, p2));
	}

	
	
	
}
