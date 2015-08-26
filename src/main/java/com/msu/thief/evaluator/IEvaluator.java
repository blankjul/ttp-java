package com.msu.thief.evaluator;

import com.msu.thief.variable.TravellingThiefProblem;

/**
 * Interface for evaluating any input to Double
 * @param <T> input of the evaluator
 */
public interface IEvaluator<T> {
	
	public Double evaluate(TravellingThiefProblem problem, T input);

}
