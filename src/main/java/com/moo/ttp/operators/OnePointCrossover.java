package com.moo.ttp.operators;

import com.moo.ttp.util.Pair;
import com.moo.ttp.util.Rnd;

public class OnePointCrossover {

	public static <T> Pair<T[], T[]> crossover(T[] p1, T[] p2, int point) {
		T[] c1 = p1.clone();
		T[] c2 = p2.clone();
		for (int i = point; i < p1.length; i++) {
			c2[i] = p1[i];
			c1[i] = p2[i];
		}
		return Pair.create(c1, c2);
	}

	public static <T> Pair<T[], T[]> crossover(T[] p1, T[] p2) {
		int point = Rnd.rndInt(1, p1.length - 2);
		return crossover(p1, p2, point);
	}

}
