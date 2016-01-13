package com.msu.thief.ea;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.msu.thief.ea.tour.factory.OptimalTourFactory;
import com.msu.thief.problems.variable.Tour;

public class OptimalTourFactoryTest extends Operator {
	
	
	@Test
	public void testFactory() {
		OptimalTourFactory fac = new OptimalTourFactory(thief, rand);
		assertEquals(Tour.createFromString("0,1,2,3"), fac.create());
	}
	
	
	
	
	

}
