package com.moo.ttp.problems;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.moo.ttp.model.Item;
import com.moo.ttp.model.Map;
import com.moo.ttp.util.Pair;


public class TTPTest extends TestCase {
	
	private TravellingThiefProblem ttp;
	
	@Before
    public void setUp() {
		Map m = new Map(4);
        m.set(0,1,5);
        m.set(0,2,6);
        m.set(0,3,6);
        m.set(1,2,5);
        m.set(1,3,6);
        m.set(2,3,4);
        ttp  = new TravellingThiefProblem(m, 3);
        ttp.addItem(2, new Item(10, 3));
        ttp.addItem(2, new Item(4, 1));
        ttp.addItem(2, new Item(4, 1));
        ttp.addItem(1, new Item(2, 2));
        ttp.addItem(2, new Item(3, 3));
        ttp.addItem(3, new Item(2, 2));
    }
	
	
	@Test
	public void testEvaluateFunctionG3() {
		Pair<Double,Double> p = ttp.evaluate(new Integer[] {0,1,2,3}, new Boolean[] {false, false, false, false, false, false});
		assertEquals(20.0, p.first, 0.01);
		assertEquals(0.0, p.second, 0.01);
	}
	
	@Test
	public void testEvaluateFunctionG2() {
		Pair<Double,Double> p = ttp.evaluate(new Integer[] {0,1,3,2}, new Boolean[] {false, true, false, false, false, false});
		assertEquals(23.57, p.first, 0.01);
		assertEquals(3.65, p.second, 0.01);
	}
	
	@Test
	public void testEvaluateFunctionG1() {
		Pair<Double,Double> p = ttp.evaluate(new Integer[] {0,1,3,2}, new Boolean[] {false, true, true, false, false, false});
		assertEquals(30.0, p.first, 0.01);
		assertEquals(6.83, p.second, 0.01);
	}
	
	@Test
	public void testEvaluateFunctionOverload() {
		Pair<Double,Double> p = ttp.evaluate(new Integer[] {0,1,3,2}, new Boolean[] {true, true, true, false, true, true});
		assertEquals(0.0, p.second, 0.01);
	}
	

}
