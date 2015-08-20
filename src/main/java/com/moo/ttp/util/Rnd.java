package com.moo.ttp.util;

import java.util.Random;

/**
 * This is a class that provides some random methods by using the same
 * Random Constructor object.
 *
 */
public class Rnd {
	
	static Random r = new Random();
	
	public static int rndInt(int max) {
		return r.nextInt(max);
	}
	
	public static int rndInt(int min, int max) {
		return r.nextInt(max - min + 1) + min;
	}
	
	public static double rndDouble() {
		return r.nextDouble();
	}
	
	public static double rndDouble(double min, double max) {
		return min + (max - min) * r.nextDouble();
	}
	

	

}
