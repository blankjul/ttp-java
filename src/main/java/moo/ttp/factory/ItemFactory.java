package moo.ttp.factory;

import java.util.ArrayList;
import java.util.Collection;

import moo.ttp.model.Item;
import moo.ttp.util.Rnd;

public class ItemFactory {
	
	public enum TYPE {UNCORRELATED, WEAKLY_CORRELATED, STRONGLY_CORRELATED};
	
	
	public static Item create(TYPE t, int maxValue) {
		int weight = Rnd.rndInt(0, maxValue);
		int profit = 0;
		
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
		Item i = new Item(profit, weight);
		return i;
	}
	
	
	public static Collection<Item> create(TYPE t, int n, int maxValue) {
		ArrayList<Item> items = new  ArrayList<Item>(n);
		while (items.size() < n) {
			Item i = create(t, maxValue);
			items.add(i);
		}
		return items;
		
	}
	
	
	
	/*
	 * 
	 * 
	 *   auto funcRandomRange = [] (int min, int max) { return min + rand() % (max - min); };

        while (items.size() < numberOfItems) {

            // calculate the weight in between the bounds
            double weight = (rand() % bounds) + 1;
            double value;
            int abs;


            switch (type) {
                // just calculate another value in the bounds
                case KnapsackType::UNCORRELATED: value = (rand() % bounds) + 1;
                    break;
                // make sure that the difference of weight and value are only 10% of the whole range.
                case KnapsackType::WEAKLY_CORRELATED:
                    abs = bounds * 0.1;
                    if (abs == 0) abs = 1;
                    value = weight + funcRandomRange(0, 2 * abs) - abs;
                    break;
                // same as weakly but only 1% is no allowed
                case KnapsackType::STRONGLY_CORRELATED:
                    abs = bounds * 0.01;
                    if (abs == 0) abs = 1;
                    value = weight + funcRandomRange(0, 2 * abs) - abs;
                    break;
            }

            // stay in the range of the bounds!
            if (value < 0 || value > bounds) continue;

            sum += weight;
            ItemPtr iPtr = make_shared<Item>(value, weight);
            items.push_back(iPtr);
	 * 
	 */

}
