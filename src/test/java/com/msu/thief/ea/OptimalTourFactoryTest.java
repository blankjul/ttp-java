package com.msu.thief.ea;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.msu.thief.ea.tour.factory.ThiefOptimalTourFactory;
import com.msu.thief.problems.variable.Tour;

public class OptimalTourFactoryTest extends Operator {
	
	
	@Test
	public void testFactory() {
		ThiefOptimalTourFactory fac = new ThiefOptimalTourFactory();
		fac.initialize(thief, rand);
		assertEquals(Tour.createFromString("0,1,2,3"), fac.create());
	}
	
	
	
	
	

}
