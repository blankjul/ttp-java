package moo.ttp.operators;

import moo.ttp.util.Pair;
import moo.ttp.util.Rnd;
import moo.ttp.util.Util;

public class PMXCrossover {

	
	public static <T> Pair<T[], T[]> crossover(T[] p1, T[] p2) {
		int point = Rnd.rndInt(1, p1.length - 2);
		return crossover(p1, p2, point);
	}
	
	public static <T> Pair<T[], T[]> crossover(T[] p1, T[] p2, int point) {
		T[] c1 = crossover_(p1, p2, point);
		T[] c2 = crossover_(p2, p1, point);
		return Pair.create(c1, c2);
	}
	
	public static <T> T[] crossover_(T[] p1, T[] p2) {
		int point = Rnd.rndInt(1, p1.length - 2);
		return crossover_(p1,p2,point);
	}
	
	public static <T> T[] crossover_(T[] p1, T[] p2, int point) {
		// clone the whole chromosome
		T[] c = p1.clone();

		// until the split point perform a swap operation
		for (int i = 0; i < point; i++) {
			int index = Util.find(c, p2[i]);
			if (index == -1) throw new RuntimeException("PMX Crossover is only allowed on permuation objects!");
			Util.swap(c, i, index);
		}
		
		return c;
	}

}
