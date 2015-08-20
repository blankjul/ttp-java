package com.moo.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.moo.ttp.util.Rnd;

public class BooleanSimilarityCrossover extends AbstractCrossover<List<Boolean>> {

	@Override
	protected List<List<Boolean>> crossover_(List<Boolean> a, List<Boolean> b) {
		// copy the both list and change values
		List<Boolean> c1 = new ArrayList<Boolean>(a);
		List<Boolean> c2 = new ArrayList<Boolean>(a);

		for (int i = 0; i < a.size(); i++) {
			
			// if both has a 1 the 1 should stay for sure
			if (a.get(i) == true && b.get(i) == true) continue;
			
			// if only 1 parent has true choose random bit kipping
			if (a.get(i) == true && Rnd.rndDouble() < 0.5) a.set(i, false);
			if (b.get(i) == true && Rnd.rndDouble() < 0.5) b.set(i, false);
			
			// if it is false, it will just stay false
		}

		return new ArrayList<>(Arrays.asList(c1, c2));
	}

}
