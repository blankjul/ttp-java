package com.moo.ttp.operators.crossover;

import java.util.ArrayList;
import java.util.List;

import com.moo.ttp.util.Rnd;
import com.moo.ttp.util.Util;

/**
 * This class provides the PMX Crossover which is in long form
 * Partial-mapped Crossover where the offspring will also be a permutation vector.
 *
 * @param <T>
 */
public class PMXCrossover<T> extends PointCrossover<List<T>> {

	
	public PMXCrossover() {
		super();
	}
	
	public PMXCrossover(int point) {
		super(point);
	}


	private List<T> crossover_(List<T> p1, List<T> p2, int point) {
		// clone the whole List
		ArrayList<T> c = new ArrayList<T>(p1);

		// until the split point perform a swap operation
		for (int i = 0; i < point; i++) {
			int index = c.indexOf(p2.get(i));
			if (index == -1) throw new RuntimeException("PMX Crossover is only allowed on permuation objects!");
			Util.swap(c, i, index);
		}
		
		return c;
	}
	
	
	@Override
	protected List<List<T>> crossover_(List<T> a, List<T> b) {

		// if no point is set choose it random
		if (point == null || point >= a.size())
			point = Rnd.rndInt(1, a.size() - 2);

		// create the results
		List<List<T>> result = new ArrayList<>();
		result.add(crossover_(a, b, point));
		result.add(crossover_(b, a, point));

		return result;

	}


}
