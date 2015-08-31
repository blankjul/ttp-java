package com.msu.thief.model;

/**
 * This class represents a map with a predefined number of cities.
 * It is a symmetrical map where where the [i][j] value will always be the same
 * as the [j][i] value.
 * 
 * Also the class provides to get the minimal and the maximal distance all the time!
 *
 */
public class Map {
	
	//! distance matrix
	private double[][] distances;
	
	//! minimal distance 
	private double min = Double.MAX_VALUE;
	
	//! maximal distance
	private double max = Double.MIN_VALUE;

	/**
	 * Construct where all distance are zero.
	 * @param n
	 */
	public Map(int n) {
		super();
		this.distances = new double[n][n];
	}
	
	public double get(int i, int j) {
		return distances[i][j];
	}
	
	public Map set(int i, int j, double value) {
		// the values on the diagonal are not allowed to change
		if (i==j) return this;
		
		// set the maximal and minimal value
		if (value < min) min = value;
		if (value > max) max = value;
		
		// set the values at the matrix
		distances[i][j] = value;
		distances[j][i] = value;
		return this;
	}
	
	public int getSize() {
		return distances.length;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	/**
	 * Be careful when using this method. It trusts the user!
	 * @param distances cost matrix
	 */
	public void setDistances(double[][] distances) {
		this.distances = distances;
	}
	
	
	


}
