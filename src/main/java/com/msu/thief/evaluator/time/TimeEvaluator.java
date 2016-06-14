package com.msu.thief.evaluator.time;

import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.Tour;

/**
 * The TimeCalculator provides an interface for calculating the time of a given
 * tour.
 */
public abstract class TimeEvaluator {

	public double evaluate(AbstractThiefProblem problem, Tour tour, Pack pack) {
		return evaluate_(problem, tour, pack).getTime();
	}
	
	public abstract TourInformation evaluate_(AbstractThiefProblem problem, Tour tour, Pack pack);

}
