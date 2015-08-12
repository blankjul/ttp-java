package com.moo.ttp.problems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.moo.ttp.model.Item;
import com.moo.ttp.model.ItemCollection;
import com.moo.ttp.model.Map;
import com.moo.ttp.problems.ttp.TravellingThiefProblem;
import com.moo.ttp.problems.ttp.TravellingThiefProblemSettings;
import com.moo.ttp.util.Pair;


public class TravellingThiefProblemTest {
	
	private TravellingThiefProblem ttp;
	
	@Before
    public void setUp() {
		Map m = new Map(4).set(0,1,5).set(0,2,6).set(0,3,6).set(1,2,5).set(1,3,6).set(2,3,4);
        ItemCollection<Item> items = new ItemCollection<Item>();
        items.add(2, new Item(10, 3));
        items.add(2, new Item(4, 1));
        items.add(2, new Item(4, 1));
        items.add(1, new Item(2, 2));
        items.add(2, new Item(3, 3));
        items.add(3, new Item(2, 2));
        TravellingThiefProblemSettings s = new TravellingThiefProblemSettings(m, items, 3);
        s.setProfitCalculator("com.moo.ttp.problems.ttp.profit.ExponentialProfitCalculator");
        ttp = new TravellingThiefProblem(s);
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
