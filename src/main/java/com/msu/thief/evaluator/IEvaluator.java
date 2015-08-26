package com.msu.thief.evaluator;

/**
 * Interface for evaluating any input to Double
 * @param <T> input of the evaluator
 */
public interface IEvaluator<I, O> {
	
	public O evaluate(I input);

}
