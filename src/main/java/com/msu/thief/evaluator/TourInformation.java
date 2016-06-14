package com.msu.thief.evaluator;

import java.util.List;
import java.util.Map;

import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

/**
 * This class provides advanced information of the time evaluation.
 *
 */
public class TourInformation {

	// ! problem which the information belongs to
	protected AbstractThiefProblem problem;

	// ! variable of which the result was calculated
	protected TTPVariable variable;

	// ! cache of the tour map
	protected Map<Integer, Integer> mCities;

	// ! speed at all the cities
	protected List<Double> speedAtCities;

	// ! time at all the cities
	protected List<Double> timeAtCities;
	
	// ! weight at all the cities
	protected List<Double> weightAtCities;

	// ! final time of the whole tour
	protected double time;


	public TourInformation(AbstractThiefProblem problem, TTPVariable variable, List<Double> speedAtCities,
			List<Double> timeAtCities, List<Double> weightAtCities, double time) {
		super();
		this.problem = problem;
		this.variable = variable;
		this.speedAtCities = speedAtCities;
		this.timeAtCities = timeAtCities;
		this.weightAtCities = weightAtCities;
		this.time = time;
	}
	

	public AbstractThiefProblem getProblem() {
		return problem;
	}

	public TTPVariable getVariable() {
		return variable;
	}

	public double getTime() {
		return time;
	}


	/**
	 * @return list of the speeds. the order is according to the tour.
	 */
	public List<Double> getSpeedAtCities() {
		return speedAtCities;
	}
	
	/**
	 * @return list of the time. the order is according to the tour.
	 */
	public List<Double> getTimeAtCities() {
		return timeAtCities;
	}
	
	/**
	 * @return list of the time. the order is according to the tour.
	 */
	public List<Double> getWeightAtCities() {
		return weightAtCities;
	}
	
	public double getTimeAtCity(int city) {
		if (mCities == null) mCities = variable.getTour().getAsHash();
		int idx = mCities.get(city);
		return timeAtCities.get(idx);
	}

	public double getSpeedAtCity(int city) {
		if (mCities == null) mCities = variable.getTour().getAsHash();
		return speedAtCities.get(mCities.get(city));
	}
	
	public double getWeightAtCity(int city) {
		if (mCities == null) mCities = variable.getTour().getAsHash();
		return weightAtCities.get(mCities.get(city));
	}


	@Override
	public String toString() {
		return "TourInformation [problem=" + problem + ", variable=" + variable + ", mCities=" + mCities
				+ ", speedAtCities=" + speedAtCities + ", timeAtCities=" + timeAtCities + ", weightAtCities="
				+ weightAtCities + ", time=" + time + "]";
	}
	
	

}
