package com.msu.thief.variable;

import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.util.Pair;

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
	protected void mutate_(Pair<IVariable,IVariable> a) {
		a.first = mTour.mutate(a.first);
		a.second = mPackingPlan.mutate(a.second);
	}
	
	
	
	
	
}
