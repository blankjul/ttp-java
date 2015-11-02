package com.msu.thief.variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.operators.AbstractCrossover;
import com.msu.moo.util.Pair;
import com.msu.moo.util.Random;

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
	protected List<Pair<IVariable,IVariable>> crossover_(Pair<IVariable,IVariable> a, Pair<IVariable,IVariable> b, Random rand) {
		List<IVariable> offTours = cTour.crossover(a.first, b.first, rand);
		List<IVariable> offPlans = cPackingPlan.crossover(a.second, b.second, rand);
		
		Pair<IVariable,IVariable> p1 = Pair.create(offTours.get(0), offPlans.get(0));
		Pair<IVariable,IVariable> p2 = Pair.create(offTours.get(1), offPlans.get(1));
		
		return new ArrayList<Pair<IVariable,IVariable>>(Arrays.asList(p1, p2));
	}

	
	
	
}
