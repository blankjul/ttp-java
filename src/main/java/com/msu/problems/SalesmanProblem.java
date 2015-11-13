package com.msu.problems;

import java.util.HashSet;
import java.util.List;

import com.msu.model.AProblem;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.ThiefUtil;
import com.msu.util.exceptions.EvaluationException;

/**
 * This class defines the TravellingSalesmanProblem which aims to minimize the
 * tour distance of a salesman on a given map.
 */
public class SalesmanProblem extends AProblem<Tour<?>> implements ICityProblem{

	// ! Map on which the salesman could plan his tour
	protected SymmetricMap map;

	
	/**
	 * Construct a TravellingSalesmanProblem by using a predefined map which
	 * provides a distance matrix.
	 * 
	 * @param map
	 *            object which defines the distances
	 */
	public SalesmanProblem(SymmetricMap map) {
		super();
		this.map = map;
	}

	/**
	 * Check if the tour is to short or to long for sure
	 */
	public static void checkTourSize(int size, List<Integer> pi) {
		if (pi.size() != size)
			throw new EvaluationException(String.format("Map has %s cities but input %s!", size, pi.size()));

	}
	
	/**
	 * Check if the tour has no duplicates and contains all the needed indices
	 */
	
	public static void checkTourValidtiy(List<Integer> pi) {
		HashSet<Integer> hash = new HashSet<Integer>();
		Integer duplicate = ThiefUtil.getDuplicate(hash, pi);
		if (duplicate != null) {
			throw new EvaluationException(String.format("Tour is not a real permuation: city %s is visited twice!", duplicate));
		}
		for (int i = 0; i < pi.size(); i++) {
			if (!hash.contains(i)) 
				throw new EvaluationException(String.format("Tour is not valid because city %s is not visited!", i));
		}
	}
	

	

	/**
	 * Evaluate the tour of the sales man by a given permutation vector. If the
	 * vector has a value twice (it is no permutation)
	 * 
	 * @param pi
	 *            permutation on which determines the tour. 
	 */
	public Double evaluate(List<Integer> pi) {

		// check if the input is correct
		checkTourSize(map.getSize(), pi);
		checkTourValidtiy(pi);
		
		double length = 0;
		for (int i = 0; i < pi.size() - 1; i++) {
			// sum up the length of the tour
			length += map.get(pi.get(i), pi.get(i+1));
		}

		// walk from last city to first
		length += map.get(pi.get(pi.size() - 1) , pi.get(0));

		return length;
	}

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

	public int numOfCities() {
		return map.getSize();
	}

	@Override
	protected void evaluate_(Tour<?> var, List<Double> objectives, List<Double> constraintViolations) {
		Double length = evaluate(var.encode());
		objectives.add(length);
	}

	public SymmetricMap getMap() {
		return map;
	}

	@Override
	public double getMaxSpeed() {
		return 1.0;
	}
	
	

}
