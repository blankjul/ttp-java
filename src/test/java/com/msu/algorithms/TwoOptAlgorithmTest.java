package com.msu.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.TwoOptFactory;

public class TwoOptAlgorithmTest {

	
	@Test
	public void testTwoOptSwap() {
		Tour<?> t = new StandardTour("[0,1,2,3,4,5,6,7]");
		assertEquals(new StandardTour("[0,1,2,6,5,4,3,7]"), TwoOptFactory.twoOptSwap(t.encode(), 3, 6));
	}
	
	@Test
	public void testTwoOptSwapSameIndexNoException() {
		TwoOptFactory.twoOptSwap(new StandardTour("[0,1,2,3,4,5,6,7]").encode(), 3, 3);
	}
	
	@Test
	public void testTwoOptKisMax() {
		TwoOptFactory.twoOptSwap(new StandardTour("[0,1,2,3,4,5,6,7]").encode(), 3, 7);
	}

}
