package com.msu.thief.evaluator.time;

import com.msu.thief.evaluator.TourInformation;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.Tour;

/**
 * The TimeCalculator provides an interface for calculating the time of a given
 * tour.
 */
public abstract class TimeEvaluator {

	public double evaluate(ThiefProblem problem, Tour<?> tour, PackingList<?> pack) {
		return evaluate_(problem, tour, pack).getTime();
	}
	
	public abstract TourInformation evaluate_(ThiefProblem problem, Tour<?> tour, PackingList<?> pack);

}
