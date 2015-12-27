package com.msu.thief.variable;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.operators.AbstractMutation;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class TTPLocalSearchMutation extends AbstractMutation<Pair<IVariable,IVariable>>{

	//! crossover for the tour
	protected AbstractMutation<?> mTour;
	
	
	public TTPLocalSearchMutation(AbstractMutation<?> mTour) {
		super();
		this.mTour = mTour;
	}

	
	@Override
	public Pair<IVariable,IVariable> mutate_(Pair<IVariable,IVariable> a, IProblem problem, MyRandom rand, IEvaluator eval) {
		IVariable tour = mTour.mutate(a.first, problem, rand);
		final int evaluations = 100;
		IVariable b = RecombinationUtil.localSearch(problem, (Tour<?>)tour, rand, eval, evaluations);
		return Pair.create(tour, b);
	}
	
	
	
	
	
}
