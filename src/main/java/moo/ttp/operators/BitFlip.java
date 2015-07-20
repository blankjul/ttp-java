package moo.ttp.operators;

import moo.ttp.util.Rnd;


public class BitFlip {
	
	
	public static void mutate(Boolean[] b) {
		double prob = 1 / b.length;
		for (int i = 0; i < b.length; i++) {
			if (Rnd.rndDouble() < prob) b[i] = !b[i];
		}
	}

}
