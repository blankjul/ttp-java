package com.msu.thief.factory.items;

import com.msu.moo.util.Random;
import com.msu.thief.model.Item;

public class ItemFactory extends AbstractItemFactory {

	// ! this defines how the items are created dependent on the correlation.
	public static enum CORRELATION_TYPE {
		UNCORRELATED, WEAKLY_CORRELATED, STRONGLY_CORRELATED
	};


	// ! default maximal values for the weight and profit
	protected int maximalValue = 1000;

	// ! default correlation type is uncorrelated
	protected CORRELATION_TYPE corrType = CORRELATION_TYPE.UNCORRELATED;

	// ! default maximal tour time. has to be given as a parameter
	protected Double maximalTourTime = null;

	
	public ItemFactory() {
		this.corrType = CORRELATION_TYPE.UNCORRELATED;
	}

	
	
	public ItemFactory(CORRELATION_TYPE corrType) {
		super();
		this.corrType = corrType;
	}


	public ItemFactory(CORRELATION_TYPE corrType, int maximalValue) {
		super();
		this.maximalValue = maximalValue;
		this.corrType = corrType;
	}




	@Override
	public Item create() {

		Random rnd = Random.getInstance();

		// fix the weight value
		int weight = rnd.nextInt(1, maximalValue);
		int profit = 0;

		// calculate the profit value
		switch (corrType) {
		case UNCORRELATED:
			profit = rnd.nextInt(1, maximalValue);
			break;
		case WEAKLY_CORRELATED:
			int epsW = (int) ((maximalValue * 0.05 == 0) ? 1 : maximalValue * 0.05);
			profit = rnd.nextInt(weight - epsW, weight + epsW);
			break;
		case STRONGLY_CORRELATED:
			int epsS = (int) ((maximalValue * 0.005 == 0) ? 1 : maximalValue * 0.005);
			profit = rnd.nextInt(weight - epsS, weight + epsS);
			break;

		}

		// stay in between the boundaries.
		if (profit < 0)
			profit = 0;
		if (profit > maximalValue)
			profit = maximalValue;

		// set the dropping of the item
		double dropping = 0;
		if (maximalTourTime == null) dropping = rnd.nextDouble(0.2, 0.8);
		else dropping = profit / maximalTourTime;

		
		Item i = new Item(profit, weight, dropping);
		return i;
	}


	public int getMaximalValue() {
		return maximalValue;
	}


	public void setMaximalValue(int maximalValue) {
		this.maximalValue = maximalValue;
	}


	public CORRELATION_TYPE getCorrType() {
		return corrType;
	}


	public void setCorrType(CORRELATION_TYPE corrType) {
		this.corrType = corrType;
	}


	public Double getMaximalTourTime() {
		return maximalTourTime;
	}


	public void setMaximalTourTime(Double maximalTourTime) {
		this.maximalTourTime = maximalTourTime;
	}
	
	
	public static void main(String[] args) {
		ItemFactory fac = new ItemFactory(CORRELATION_TYPE.UNCORRELATED);
		fac.setMaximalValue(100);
		for (int i = 0; i < 10; i++) {
			System.out.println(fac.create());
		}
	}
	

}
