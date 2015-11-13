package com.msu.thief.model.tour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.interfaces.IVariable;
import com.msu.operators.crossover.SinglePointCrossover;
import com.msu.problems.ThiefProblem;
import com.msu.thief.variable.tour.PositionDecodedTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.PositionDecodedTourFactory;
import com.msu.util.Random;

public class PositionDecodedTourTest {

	public class TTPMOCK extends ThiefProblem {

		public TTPMOCK() {
			super();
		}
		@Override
		public int numOfCities() {
			return 4;
		}
		
	}

	@Test
	public void testRandomCreation() {
		for (int i = 0; i < 100; i++) {
			Tour<?> t = new PositionDecodedTourFactory<>().next(new TTPMOCK(), new Random());
			@SuppressWarnings("unchecked")
			ArrayList<Integer> list = (ArrayList<Integer>) t.get();
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
	
	
	
	@Test
	public void testCrossoverOfPDT() {
		System.out.println("SinglePointCrossover");
		PositionDecodedTour p1 = new PositionDecodedTour(new ArrayList<Integer>(Arrays.asList(0,0,0,3,0)));
		PositionDecodedTour p2 = new PositionDecodedTour(new ArrayList<Integer>(Arrays.asList(0,1,1,2,4)));
		List<IVariable> offs = new SinglePointCrossover<Integer> ().crossover(p1,p2, new Random());
		
		System.out.println(p1.get() + " -> " + p1.encode());
		System.out.println(p2.get() + " -> " + p2.encode());
		
		PositionDecodedTour off1 = ((PositionDecodedTour)offs.get(0));
		PositionDecodedTour off2 = ((PositionDecodedTour)offs.get(1));
		System.out.println("--------------------------");
		System.out.println(off1.get() + " -> " + off1.encode());
		System.out.println(off2.get() + " -> " + off2.encode());
		System.out.println("--------------------------");
		System.out.println("--------------------------");
	}
	
	/*
	@Test
	public void testSBXCrossoverOfPDT() {
		System.out.println("SimulatedBinaryCrossoverForInteger");
		PositionDecodedTour p1 = new PositionDecodedTour(new ArrayList<Integer>(Arrays.asList(0,0,0,0,0)));
		PositionDecodedTour p2 = new PositionDecodedTour(new ArrayList<Integer>(Arrays.asList(0,1,2,3,4)));
		List<IVariable> offs = new SimulatedBinaryCrossoverForInteger().crossover(p1,p2);
		
		System.out.println(p1.get() + " -> " + p1.encode());
		System.out.println(p2.get() + " -> " + p2.encode());
		
		PositionDecodedTour off1 = ((PositionDecodedTour)offs.get(0));
		PositionDecodedTour off2 = ((PositionDecodedTour)offs.get(1));
		System.out.println("--------------------------");
		System.out.println(off1.get() + " -> " + off1.encode());
		System.out.println(off2.get() + " -> " + off2.encode());
		System.out.println("--------------------------");
		System.out.println("--------------------------");
	}
	*/


	@Test
	public void testEncodingLive() {
		PositionDecodedTour t = new PositionDecodedTour(new ArrayList<Integer>(Arrays.asList(0,1,1,3,4)));
		System.out.println("live");
		System.out.println(t.encode());
	}
	
	
}
