package com.moo.ttp.experiment.factory;

import java.util.ArrayList;
import java.util.Collection;

import com.moo.ttp.model.item.Item;
import com.moo.ttp.util.Rnd;

/**
 * This class is the item factory which allows to create different item types.
 * There are UNCORRELATED, WEAKLY_CORRELATED, STRONGLY_CORRELATED which determine the 
 * depends between the profit and the weight of each item.
 * 
 *
 */
public class ItemFactory {
	
	public enum TYPE {UNCORRELATED, WEAKLY_CORRELATED, STRONGLY_CORRELATED};
	
	
	public static Item create(TYPE t, int maxValue, Double maxTourTime) {
		
		// fix the weight value
		int weight = Rnd.rndInt(0, maxValue);
		int profit = 0;
		
		// calculate the profit value
		switch (t) {
        case UNCORRELATED: 
        	profit = Rnd.rndInt(0, maxValue);
            break;
        case WEAKLY_CORRELATED: 
        	int epsW = (int) ((maxValue * 0.05 == 0) ? 1 : maxValue * 0.05);
        	profit = Rnd.rndInt(weight - epsW, weight + epsW);
            break;
        case STRONGLY_CORRELATED:
        	int epsS = (int) ((maxValue * 0.005 == 0) ? 1 : maxValue * 0.005);
        	profit = Rnd.rndInt(weight - epsS, weight + epsS);
        	break;

		}
		if (profit < 1) profit = 1;
		if (profit > maxValue) profit = maxValue;
		
		// set the dropping of the item
		double dropping = 0;
		if (maxTourTime != null) dropping = profit / maxTourTime;
		else dropping = Rnd.rndDouble(0.2, 0.8);
		
		Item i = new Item(profit, weight, dropping);
		return i;
	}
	
	
	
	public static Collection<Item> create(TYPE t, int n, int maxValue) {
		ArrayList<Item> items = new  ArrayList<Item>(n);
		while (items.size() < n) {
			Item i = create(t, maxValue, null);
			items.add(i);
		}
		return items;
	}
	
	
	

}
