package com.moo.ttp.operators.crossover;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PMXCrossoverTest {

	PMXCrossover<Integer> c = new PMXCrossover<Integer>(3);

	private List<Integer> a;
	private List<Integer> b;

	@Before
	public void setUp() {
		a = new ArrayList<>(Arrays.asList(5,7,1,3,6,4,2));
		b = new ArrayList<>(Arrays.asList(4,6,2,7,3,1,5));
	}

	@Test
	public void testPMXCrossover() {
		List<List<Integer>> result = c.crossover(a, b);
		assertEquals(new ArrayList<Integer>(Arrays.asList(4,6,2,3,7,5,1)), result.get(0));
		assertEquals(new ArrayList<Integer>(Arrays.asList(5,7,1,6,3,2,4)), result.get(1));
	}


}
