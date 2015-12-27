package com.msu.thief.heuristics;

import com.msu.interfaces.IEvaluator;
import com.msu.thief.problems.ThiefProblemWithFixedTour;

public abstract class ItemHeuristic implements IHeuristic<Integer>{

	
	protected ThiefProblemWithFixedTour problem;
	
	protected IEvaluator evaluator;

	public ItemHeuristic(ThiefProblemWithFixedTour problem, IEvaluator evaluator) {
		super();
		this.problem = problem;
		this.evaluator = evaluator;
	}
	
	

}
