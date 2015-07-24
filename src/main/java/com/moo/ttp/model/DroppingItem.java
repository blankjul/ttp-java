package com.moo.ttp.model;


public class DroppingItem extends Item {

	private double dropping;

	public DroppingItem(int profit, int weight) {
		this(profit, weight, 0);
	}

	public DroppingItem(int profit, int weight, double dropping) {
		super(profit, weight);
		this.dropping = dropping;
	}

	
	public double getDropping() {
		return dropping;
	}

	public void setDropping(double dropping) {
		this.dropping = dropping;
	}
	
	
	
	
	
		

}
