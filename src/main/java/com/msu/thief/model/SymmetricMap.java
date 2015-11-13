package com.msu.thief.model;

import com.msu.util.rounding.IRounding;

/**
 * This class represents a map with a predefined number of cities.
 * It is a symmetrical map where where the [i][j] value will always be the same
 * as the [j][i] value.
 * 
 * Also the class provides to get the minimal and the maximal distance all the time!
 *
 */
public class SymmetricMap {
	
	//! distance matrix
	protected double[][] distances;
	
	//! minimal distance 
	protected double min = Double.MAX_VALUE;
	
	//! maximal distance
	protected double max = Double.MIN_VALUE;

	
	/**
	 * Construct where all distance are zero.
	 * @param n
	 */
	public SymmetricMap(int n) {
		super();
		this.distances = new double[n][n];
	}

	
	public double get(int i, int j) {
		return distances[i][j];
	}
	
	public SymmetricMap set(int i, int j, double value) {
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
	
	/**
	 * Multiplies all the values of a map with a specific value
	 * @param value multiplier
	 */
	public SymmetricMap multipleCosts(double value) {
		double[][] d = new double[distances.length][distances.length];
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances.length; j++) {
				d[i][j] = distances[i][j] * value;
			}
		}
		SymmetricMap result = new SymmetricMap(distances.length);
		result.setDistances(d);
		result.max = this.max * value;
		result.min = this.min * value;
		return result;
	}
	
	/**
	 * Round all the values by a given method.
	 * @param calcRounded rounder which is executed
	 */
	public void round(IRounding calcRounded) {
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances.length; j++) {
				distances[j][i] = calcRounded.execute(distances[j][i]);
			}
		}
	}


	public final double[][] getDistances() {
		return distances;
	}
	
	
	public SymmetricMap copy() {
		SymmetricMap result = new SymmetricMap(this.getSize());
		for (int i = 0; i < distances.length; i++) {
			for (int j = i; j < distances.length; j++) {
				 result.set(i, j, distances[i][j]);
			}
		}
		return result;
	}
	
	
	


}
