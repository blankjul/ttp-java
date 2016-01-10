package com.msu.thief.heuristics;

import java.util.Map;

import com.msu.interfaces.IEvaluator;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.util.Util;

public abstract class ItemHeuristic implements IHeuristic<Integer>{

	
	protected ThiefProblemWithFixedTour problem;
	
	protected IEvaluator evaluator;

	public ItemHeuristic(ThiefProblemWithFixedTour problem, IEvaluator evaluator) {
		super();
		this.problem = problem;
		this.evaluator = evaluator;
	}

	@Override
	public Map<Integer, Double> calc() {
		return calc(Util.createIndex(problem.numOfItems()));
	}
	
	
	
	

}
