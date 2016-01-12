package com.msu.thief.ea;

import com.msu.util.MyRandom;
import com.msu.util.Pair;

public class RecombinationUtil {

	/**
	 * @param rand random generator
	 * @param lower bound for the two number
	 * @param upper bound for the two number
	 * @return Two distinct values where the first one is lower than the last one
	 */
	public static Pair<Integer, Integer> calcBounds(MyRandom rand, int lower, int upper) {
		
		int i = rand.nextInt(lower, upper);
		
		// search for another index
		int j = i;
		while (j == i) {
			j = rand.nextInt(1, upper);
		}
		
		// ensure that i < j
		if (j < i) {
			int tmp = i;
			i = j;
			j = tmp;
		}
		
		return Pair.create(i, j);
	}
	
	
}
