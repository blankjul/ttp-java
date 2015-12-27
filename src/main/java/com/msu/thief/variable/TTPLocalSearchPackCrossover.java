package com.msu.thief.variable;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.operators.AbstractCrossover;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class TTPLocalSearchPackCrossover extends AbstractCrossover<Pair<IVariable,IVariable>>{

	//! crossover for the tour
	protected AbstractCrossover<?> cTour;
	

	public TTPLocalSearchPackCrossover(AbstractCrossover<?> cTour) {
		super();
		this.cTour = cTour;
	}
	
	
	@Override
	public List<Pair<IVariable,IVariable>> crossover_(Pair<IVariable,IVariable> a, Pair<IVariable,IVariable> b, IProblem problem,  MyRandom rand, IEvaluator eval) {
		List<IVariable> offTours = cTour.crossover(a.first, b.first, problem,rand);
		
		List<Pair<IVariable,IVariable>> result = new ArrayList<Pair<IVariable,IVariable>>();
		
		final int evaluations = 100;
		
		for(IVariable tour : offTours) {
			IVariable pack = RecombinationUtil.localSearch(problem, (Tour<?>) tour, rand, eval, evaluations);
			result.add(Pair.create(tour, pack));
		}
		
		System.out.println(eval.numOfEvaluations());
		return result;
	}

	
	
	
}
