package com.moo.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.moo.operators.crossover.EdgeRecombinationCrossover;

public class EdgeRecombinationCrossoverTest {

	private class EdgeRecombinationCrossoverMock<T> extends EdgeRecombinationCrossover<T> {

		public List<List<T>> crossover_(List<T> p1, List<T> p2) {
			return super.crossover_(p1, p2);
		}

	}

	/*
	@Test
	public void testEdgeRecombinationCrossover() {
		List<Character> a = new ArrayList<>(Arrays.asList('A', 'B', 'F', 'E', 'D', 'G', 'C'));
		List<Character> b = new ArrayList<>(Arrays.asList('G', 'F', 'A', 'B', 'C', 'D', 'E'));
		assertEquals(new ArrayList<Character>(Arrays.asList('A', 'B', 'F', 'E', 'G', 'C', 'D')), new EdgeRecombinationCrossoverMock<Character>().crossover_(a, b));
	}
	*/
	
	@Test
	public void testEdgeRecombinationCrossoverNoError() {
		List<Integer> a = new ArrayList<>(Arrays.asList(0, 2, 6, 14, 15, 4, 3, 16, 1, 12, 17, 8, 18, 19, 10, 7, 9, 5, 11, 13));
		List<Integer> b = new ArrayList<>(Arrays.asList(0, 2, 14, 16, 11, 17, 3, 12, 6, 9, 7, 13, 4, 15, 18, 1, 5, 10, 8, 19));
		List<List<Integer>> l = new EdgeRecombinationCrossoverMock<Integer>().crossover(a, b);
		System.out.println(l.get(0));
	}
	


}
