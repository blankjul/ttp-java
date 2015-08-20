package com.moo.ttp.model.tour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class PositionDecodedTourTest {



	@Test
	public void testRandomCreation() {
		for (int i = 0; i < 100; i++) {
			ArrayList<Integer> list = (ArrayList<Integer>) new PositionDecodedTour(null).random(4).get();
			assertTrue(list.get(0) == 0);
			assertTrue(list.get(1) <= 1);
			assertTrue(list.get(2) <= 2);
			assertTrue(list.get(3) <= 3);
		}
	}
	
	@Test
	public void testEncoding1() {
		PositionDecodedTour t = new PositionDecodedTour(new ArrayList<Integer>(Arrays.asList(0,0,2,0)));
		assertEquals(t.encode(), new ArrayList<Integer>(Arrays.asList(3,1,0,2)));
	}
	
	@Test
	public void testEncoding2() {
		PositionDecodedTour t = new PositionDecodedTour(new ArrayList<Integer>(Arrays.asList(0,1,2,3)));
		assertEquals(t.encode(), new ArrayList<Integer>(Arrays.asList(0,1,2,3)));
	}
	
	@Test(expected=RuntimeException.class)
	public void testEncodingErrorIndexToHigh() {
		PositionDecodedTour t = new PositionDecodedTour(new ArrayList<Integer>(Arrays.asList(1,0,0,0)));
		t.encode();
	}
	


	
}
