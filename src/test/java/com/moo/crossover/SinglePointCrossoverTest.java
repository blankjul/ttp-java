package com.moo.crossover;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.moo.operators.crossover.SinglePointCrossover;

public class SinglePointCrossoverTest {


	private class SinglePointCrossoverMock extends SinglePointCrossover<Integer> {

		public List<List<Integer>> crossover_(List<Integer> p1, List<Integer> p2, int point) {
			return super.crossover_(p1, p2, point);
		}

	}

	SinglePointCrossoverMock c = new SinglePointCrossoverMock();
	

	private List<Integer> a;
	private List<Integer> b;

	@Before
	public void setUp() {
		a = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
		b = new ArrayList<>(Arrays.asList(4, 3, 2, 1));
	}

	@Test
	public void testOnePointCrossover() {
		List<List<Integer>> result = c.crossover_(a, b, 1);
		assertEquals(new ArrayList<Integer>(Arrays.asList(1,3,2,1)), result.get(0));
		assertEquals(new ArrayList<Integer>(Arrays.asList(4,2,3,4)), result.get(1));
	}


}
