package com.msu.thief.ea;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.msu.thief.ea.operators.TourOrderedMutation;
import com.msu.thief.problems.variable.Tour;

public class OrderedMutationTest extends Operator{
	
	
	@Test
	public void testSwap() {
		Tour t = Tour.createFromString("0,1,2,3,4,5,6,7,8,9");
		TourOrderedMutation.swap(t, 5, 8, 1);
		assertEquals(Tour.createFromString("0,5,6,7,1,2,3,4,8,9"), t);
	}
	
	
	@Test
	public void testNoExceptionInRepition() {
		Tour t1 = Tour.createFromString("[0,1,2,3,4,5,6,7,8,9]");
		for (int i = 0; i < 50; i++) {
			new TourOrderedMutation().mutate(t1, rand);
		}
	}
	

}
