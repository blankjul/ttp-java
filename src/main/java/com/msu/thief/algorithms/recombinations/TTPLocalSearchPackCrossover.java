package com.msu.thief.algorithms.recombinations;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.AbstractCrossover;
import com.msu.thief.algorithms.oneplusone.OnePlusOneEAFixedTour;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class TTPLocalSearchPackCrossover extends AbstractCrossover<Pair<IVariable,IVariable>>{

	//! crossover for the tour
	protected AbstractCrossover<?> cTour;
	
	protected int evaluations;
	
	
	public TTPLocalSearchPackCrossover(AbstractCrossover<?> cTour, int evaluations) {
		super();
		this.cTour = cTour;
		this.evaluations = evaluations;
	}
	
	
	@Override
	public List<Pair<IVariable,IVariable>> crossover_(Pair<IVariable,IVariable> a, Pair<IVariable,IVariable> b, IProblem problem,  MyRandom rand, IEvaluator eval) {
		List<IVariable> offTours = cTour.crossover(a.first, b.first, problem,rand);
		
		List<Pair<IVariable,IVariable>> result = new ArrayList<Pair<IVariable,IVariable>>();
		
		final int evaluations = 10000;
		
		for(IVariable tour : offTours) {
			
			Solution next = new OnePlusOneEAFixedTour().run___(
					new ThiefProblemWithFixedTour((AbstractThiefProblem) problem, (Tour<?>) tour), new Evaluator(evaluations), rand);
			
			//IVariable pack = RecombinationUtil.localSearch(problem, (Tour<?>) tour, rand, eval, evaluations);
			
			result.add(Pair.create(tour, next.getVariable()));
		}
		
		//System.out.println(eval.numOfEvaluations());
		return result;
	}

	
	
	
}
