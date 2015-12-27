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
		List<IVariable> offTours = cTour.crossover(a.first, b.first, problem,rand);
		List<IVariable> offPlans = cPackingPlan.crossover(a.second, b.second, problem, rand);
		
		Pair<IVariable,IVariable> p1 = Pair.create(offTours.get(0), offPlans.get(0));
		Pair<IVariable,IVariable> p2 = Pair.create(offTours.get(1), offPlans.get(1));
		
		return new ArrayList<Pair<IVariable,IVariable>>(Arrays.asList(p1, p2));
	}

	
	
	
}
