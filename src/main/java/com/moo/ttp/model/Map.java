package com.moo.ttp.model;

public class Map {
	
	private double[][] distances;
	
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;

	public Map(int n) {
		super();
		this.distances = new double[n][n];
	}
	
	public double get(int i, int j) {
		return distances[i][j];
	}
	
	public Map set(int i, int j, double value) {
		if (value < min) min = value;
		if (value > max) max = value;
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
	


}
