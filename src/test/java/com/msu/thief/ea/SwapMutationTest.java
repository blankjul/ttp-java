package com.msu.thief.ea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.msu.thief.ea.tour.mutation.SwapMutation;
import com.msu.thief.problems.variable.Tour;

public class SwapMutationTest extends Operator{
	
	
	@Test
	public void testSwapAll() {
		Tour t = Tour.createFromString("[0,1,2,3,4,5,6,7,8,9]");
		SwapMutation.swap(t, 1, 9);
		assertEquals(Tour.createFromString("[0, 9, 8, 7, 6, 5, 4, 3, 2, 1]"), t);
	}
	
	
	@Test
	public void testOnlyLastTwo() {
		Tour t = Tour.createFromString("[0,1,2,3,4,5,6,7,8,9]");
		SwapMutation.swap(t, 8, 9);
		assertEquals(Tour.createFromString("[0,1,2,3,4,5,6,7,9,8]"), t);
	}
	
	
	@Test
	public void testMutation() {
		for (int i = 0; i < 50; i++) {
			Tour p = Tour.createFromString("[0,1,2,3]");
			Tour org = p.copy();
			new SwapMutation().mutate(thief, rand, p);
			assertTrue(!org.equals(p));
		}
	}
	
	
	
	

}
