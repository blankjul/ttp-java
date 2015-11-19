package com.msu.thief.variable;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.operators.AbstractMutation;
import com.msu.util.Pair;
import com.msu.util.Random;

public class TTPMutation extends AbstractMutation<Pair<IVariable,IVariable>>{

	//! crossover for the tour
	protected AbstractMutation<?> mTour;
	
	//! crossover for the packing plan
	protected AbstractMutation<?> mPackingPlan;

	
	public TTPMutation(AbstractMutation<?> mTour, AbstractMutation<?> mPackingPlan) {
		super();
		this.mTour = mTour;
		this.mPackingPlan = mPackingPlan;
	}

	
	
	@Override
	protected Pair<IVariable,IVariable> mutate_(Pair<IVariable,IVariable> a, IProblem problem, Random rand) {
		IVariable tour = mTour.mutate(a.first, problem, rand);
		IVariable b = mPackingPlan.mutate(a.second, problem, rand);
		return Pair.create(tour, b);
	}
	
	
	
	
	
}
