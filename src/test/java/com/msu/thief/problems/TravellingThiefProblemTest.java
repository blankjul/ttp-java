package com.msu.thief.problems;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;
import com.msu.thief.model.Map;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.problems.TravellingThiefProblem;
import com.msu.thief.variable.TTPVariable;

public class TravellingThiefProblemTest {

	private TravellingThiefProblem ttp;

	@Before
	public void setUp() {
		Map m = new Map(4).set(0, 1, 5).set(0, 2, 6).set(0, 3, 6).set(1, 2, 5).set(1, 3, 6).set(2, 3, 4);
		ItemCollection<Item> items = new ItemCollection<Item>();
		items.add(2, new Item(10, 3));
		items.add(2, new Item(4, 1));
		items.add(2, new Item(4, 1));
		items.add(1, new Item(2, 2));
		items.add(2, new Item(3, 3));
		items.add(3, new Item(2, 2));
		ttp = new TravellingThiefProblem(m, items, 3);
		ttp.setProfitEvaluator(new ExponentialProfitEvaluator());
	}

	@Test
	public void testEvaluateFunctionG3() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(20.0, p.getObjectives().get(0), 0.01);
		assertEquals(0.0, p.getObjectives().get(1), 0.01);
	}

	@Test
	public void testEvaluateFunctionG2() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 1, 3, 2)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(false, true, false, false, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(23.57, p.getObjectives().get(0), 0.01);
		assertEquals(-3.65, p.getObjectives().get(1), 0.01);
	}

	@Test
	public void testEvaluateFunctionG1() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 1, 3, 2)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(false, true, true, false, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(30.0, p.getObjectives().get(0), 0.01);
		assertEquals(-6.83, p.getObjectives().get(1), 0.01);
	}

	@Test
	public void testEvaluateFunctionOverload() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 1, 3, 2)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(true, true, true, false, true, true)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(0.0, p.getObjectives().get(1), 0.01);
	}

	@Test
	public void testSimpleExample() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 3, 2, 1)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(20.0, p.getObjectives().get(0), 0.01);
		assertEquals(0.0, p.getObjectives().get(1), 0.01);
	}

}
