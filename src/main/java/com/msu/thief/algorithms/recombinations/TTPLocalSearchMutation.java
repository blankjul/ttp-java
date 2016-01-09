package com.msu.thief.algorithms.recombinations;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.AbstractMutation;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class TTPLocalSearchMutation extends AbstractMutation<Pair<IVariable,IVariable>>{

	//! crossover for the tour
	protected AbstractMutation<?> mTour;
	
	protected int evaluations;
	
	public TTPLocalSearchMutation(AbstractMutation<?> mTour, int evaluations) {
		super();
		this.mTour = mTour;
		this.evaluations = evaluations;
	}

	
	@Override
	public Pair<IVariable,IVariable> mutate_(Pair<IVariable,IVariable> a, IProblem problem, MyRandom rand, IEvaluator eval) {
		IVariable tour = mTour.mutate(a.first, problem, rand);
		final int evaluations = 10000;
		
		Solution next = new OnePlusOneEAFixedTour().run___(
				new ThiefProblemWithFixedTour((AbstractThiefProblem) problem, (Tour<?>) tour), new Evaluator(evaluations), rand);
		
		return Pair.create(tour, next.getVariable());
	}
	
	
	
	
	
}
