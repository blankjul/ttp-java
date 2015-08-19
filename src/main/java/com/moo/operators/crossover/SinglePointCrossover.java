package com.moo.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.moo.ttp.util.Rnd;

/**
 * This is the single point crossover where a list with any type could but cut
 * in a half and recombined with another half.
 * 
 * [0,1,2,3,4] and [4,3,2,1,0] and point is one leads to
 * 
 * [0] + [3,2,1,0] = [0,3,2,1,0] and [4] + [1,2,3,4] = [4,1,2,3,4]
 * 
 */
public class SinglePointCrossover<T> extends AbstractCrossover<List<T>> {

	protected List<List<T>> crossover_(List<T> a, List<T> b, int point) {

		// copy the both list and change values
		List<T> c1 = new ArrayList<T>(a);
		List<T> c2 = new ArrayList<T>(b);
		for (int i = point; i < a.size(); i++) {
			c2.set(i, a.get(i));
			c1.set(i, b.get(i));
		}
		return new ArrayList<>(Arrays.asList(c1, c2));
	}
	
	
	@Override
	protected List<List<T>> crossover_(List<T> a, List<T> b) {
		return crossover_(a, b, Rnd.rndInt(1, a.size() - 2));

	}

}
