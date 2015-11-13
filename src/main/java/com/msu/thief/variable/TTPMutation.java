package com.msu.thief.variable;

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
	protected void mutate_(Pair<IVariable,IVariable> a, Random rand) {
		a.first = mTour.mutate(a.first, rand);
		a.second = mPackingPlan.mutate(a.second, rand);
	}
	
	
	
	
	
}
