package com.msu.thief.ea;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.msu.thief.ea.operators.ThiefOrderedCrossover;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.Pair;

public class OrderedCrossoverTest extends Operator{
	
	
	@Test
	public void testCrossover() {
		Tour t1 = Tour.createFromString("[0,1,2,3,4,5,6,7,8,9]");
		Tour t2 = Tour.createFromString("[0,9,8,7,6,5,4,3,2,1]");
		
		Tour result = new ThiefOrderedCrossover().crossover(t1, t2, Pair.create(2,6));
		assertEquals(Tour.createFromString("[0,6,2,3,4,5,1,9,8,7]"), result);
		
		Tour result2 = new ThiefOrderedCrossover().crossover(t1, t2, Pair.create(1,6));
		assertEquals(Tour.createFromString("[0,1,2,3,4,5,9,8,7,6]"), result2);
	}
	
	
	
	@Test
	public void testNoExceptionInRepition() {
		
		Tour t1 = Tour.createFromString("[0,1,2,3,4,5,6,7,8,9]");
		Tour t2 = Tour.createFromString("[0,9,8,7,6,5,4,3,2,1]");
		
		for (int i = 0; i < 50; i++) {
			new ThiefOrderedCrossover().crossover(t1,t2, rand);
		}
	}
	
	
	

}
