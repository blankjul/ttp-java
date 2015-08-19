package com.moo.problems;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.moo.problems.Knapsack;
import com.moo.ttp.model.item.Item;

public class KnapsackTest {

	private Knapsack k;
	private ArrayList<Item> l;

	@Before
	public void setUp() {
		l = new ArrayList<Item>(Arrays.asList(new Item(1, 1), new Item(2, 2), new Item(3, 3)));
		k = new Knapsack(3, l);
	}

	@Test
	public void testGetWeightFunction() {
		assertEquals(6, (int) Knapsack.getWeight(l, new Boolean[] { true, true, true }));
	}

	@Test
	public void testEvaluateFunctionIsNonZero() {
		assertEquals(3, (int) k.evaluate(new Boolean[] { true, true, false }));
	}

	@Test
	public void testEvaluateFunctionIsZero() {
		assertEquals(0, (int) k.evaluate(new Boolean[] { true, true, true }));
	}

	@Test (expected=RuntimeException.class) 
	public void testWrongSizeOfTour() throws RuntimeException {
		k.evaluate(new Boolean[] { true, true });
	}
	
	

}
