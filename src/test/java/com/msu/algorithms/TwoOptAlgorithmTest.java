package com.msu.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.msu.io.reader.SalesmanProblemReader;
import com.msu.moo.util.Pair;
import com.msu.moo.util.Random;
import com.msu.problems.SalesmanProblem;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.RandomTourFactory;
import com.msu.thief.variable.tour.factory.TwoOptFactory;

public class TwoOptAlgorithmTest {

	
	@Test
	public void testTwoOptSwap() {
		Tour<?> t = new StandardTour("[0,1,2,3,4,5,6,7]");
		List<Integer> swapped = TwoOptFactory.twoOptSwap(t.encode(), 3, 6);
		assertEquals(new StandardTour("[0,1,2,6,5,4,3,7]").encode(), swapped);
	}
	
	@Test
	public void testPublicationScenario() {
		SalesmanProblem tsp = new SalesmanProblemReader().read("resources/bays29.tsp");
		
		Tour<?> t = new RandomTourFactory().next(tsp, new Random(1));
		double time = tsp.evaluate(t).getObjectives(0) ;
		
		assertEquals(new StandardTour("[0, 26, 3, 1, 11, 5, 22, 20, 13, 25, 17, 21, 6, 15, 28, 2, 12, 18, 7, 8, 10, 14, 24, 16, 27, 9, 19, 4, 23]").encode(), t.encode());
		
		Pair<List<Integer>, Double> swapped = TwoOptFactory.twoOptSwap(t.encode(), 3, 10, tsp.getMap(), time);
		assertEquals(new StandardTour("[0, 26, 3, 17, 25, 13, 20, 22, 5, 11, 1, 21, 6, 15, 28, 2, 12, 18, 7, 8, 10, 14, 24, 16, 27, 9, 19, 4, 23]").encode(), swapped.first);
		assertEquals(tsp.evaluate(new StandardTour(swapped.first)).getObjectives(0) , swapped.second, 0.01);
	}
	
	@Test
	public void testFastCalcEqualToSlowVersion() {
		SalesmanProblem tsp = new SalesmanProblemReader().read("resources/bays29.tsp");
		Tour<?> t = new RandomTourFactory().next(tsp, new Random(1));
		Tour<?> fast = TwoOptFactory.optimize2Opt(tsp, t, new Random(1), true);
		Tour<?> slow = TwoOptFactory.optimize2Opt(tsp, t, new Random(1), false);
		assertEquals(fast.encode(), slow.encode());
	}
	
	
	
	/*
	@Test
	public void testTwoOptSwapSameIndexNoException() {
		TwoOptFactory.twoOptSwap(new StandardTour("[0,1,2,3,4,5,6,7]").encode(), 3, 3);
	}
	
	@Test
	public void testTwoOptKisMax() {
		TwoOptFactory.twoOptSwap(new StandardTour("[0,1,2,3,4,5,6,7]").encode(), 3, 7);
	}
	*/

}
