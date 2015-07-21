package com.moo.ttp.util;

import java.util.Random;

public class Rnd {
	
	static Random r = new Random();
	
	public static int rndInt(int min, int max) {
		Random r = new Random();
		int v = r.nextInt(max - min + 1) + min;
		return v;
	}
	
	public static double rndDouble() {
		return r.nextDouble();
	}

}
