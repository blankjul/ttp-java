package moo.ttp.model;

public class Map {
	
	private double[][] distances;

	public Map(int n) {
		super();
		this.distances = new double[n][n];
	}
	
	public double get(int i, int j) {
		return distances[i][j];
	}
	
	public void set(int i, int j, double value) {
		distances[i][j] = value;
		distances[j][i] = value;
	}
	
	public int getSize() {
		return distances.length;
	}
	
	
	

}
