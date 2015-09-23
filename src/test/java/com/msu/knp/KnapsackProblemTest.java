package com.msu.knp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.msu.knp.KnapsackProblem;
import com.msu.knp.model.Item;

public class KnapsackProblemTest {

	private KnapsackProblem k;
	private ArrayList<Item> l;

	@Before
	public void setUp() {
		l = new ArrayList<Item>(Arrays.asList(new Item(1, 1), new Item(2, 2), new Item(3, 3)));
		k = new KnapsackProblem(3, l);
	}

	@Test
	public void testGetWeightFunction() {
		assertEquals(6, (int) KnapsackProblem.getWeight(l, new ArrayList<Boolean>(Arrays.asList(true, true, true))));
	}

	@Test
	public void testEvaluateFunctionIsNonZero() {
		assertEquals(3, (int) k.evaluate( new ArrayList<Boolean>(Arrays.asList(true, true, false))));
	}

	@Test
	public void testEvaluateFunctionIsZero() {
		assertEquals(0, (int) k.evaluate( new ArrayList<Boolean>(Arrays.asList(true, true, true))));
	}

	@Test (expected=RuntimeException.class) 
	public void testWrongSizeOfTour() throws RuntimeException {
		k.evaluate( new ArrayList<Boolean>(Arrays.asList(true, true)));
	}
	
	

}
