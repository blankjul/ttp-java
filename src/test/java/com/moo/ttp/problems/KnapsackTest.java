package com.moo.ttp.problems;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.moo.ttp.model.Item;


public class KnapsackTest extends TestCase {

	private Knapsack k;
	
	@Before
    public void setUp() {
		ArrayList<Item> l = new ArrayList<Item>(Arrays.asList(new Item(1,1), new Item(2,2), new Item(3,3)));
		k = new Knapsack(3, l);
    }
	
	@Test
	public void testEvaluateFunctionIsNonZero() {
		assertEquals(3, k.evaluate(new Boolean[] {true, true, false}));
	}
	
	@Test
	public void testEvaluateFunctionIsZero() {
		assertEquals(0, k.evaluate(new Boolean[] {true, false, true}));
	}
	
	

}
