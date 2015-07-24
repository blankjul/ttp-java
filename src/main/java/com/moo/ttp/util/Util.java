package com.moo.ttp.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class Util {
	
	public static <T> void swap(T[] obj, int a, int b) {
		T tmp = obj[a];
		obj[a] = obj[b];
		obj[b] = tmp;
	}
	
	public static <T> int find(T[] array, T value) {
	    for(int i=0; i<array.length; i++) {
	    	if(array[i] == value) return i;
	    }
	    return -1;
	}
	
	public static Integer[] createRandomTour(int n) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 1; i < n; i++) indices.add(i);
		Collections.shuffle(indices);
		indices.addFirst(0);
		Integer[] pi = new Integer[indices.size()];
		for (int j = 0; j < pi.length; j++) pi[j] = indices.get(j);
		return pi;
	}
	
	public static Boolean[] createRandomPickingPlan(int n) {
		double pickingProb = Rnd.rndDouble();
		Boolean[] b = new Boolean[n];
		for (int i = 0; i < b.length; i++) {
			b[i] = Rnd.rndDouble() < pickingProb;
		}
		return b;
	}
	
	public static <T> void write(String path, T obj) {
		System.out.println("Writing " + path);
		try {
			FileWriter writer = new FileWriter(path);
			writer.write(obj.toString());
			writer.write('\n');
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
