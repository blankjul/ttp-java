package com.msu.thief;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.PackingList;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.thief.scenarios.impl.BonyadiPublicationScenario;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;

public class TravellingThiefProblemBonyadiCorrectnessTest {

	private TravellingThiefProblem ttp;

	@Before
	public void setUp() {
		ttp = new BonyadiPublicationScenario().getObject();
	}

	@Test
	public void testEvaluateFunctionG3() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(20.0, p.getObjective().get(0), 0.01);
		assertEquals(0.0, p.getObjective().get(1), 0.01);
	}

	@Test
	public void testEvaluateFunctionG2() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 1, 3, 2)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(false, true, false, false, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(23.57, p.getObjective().get(0), 0.01);
		assertEquals(-3.65, p.getObjective().get(1), 0.01);
	}

	@Test
	public void testEvaluateFunctionG1() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 1, 3, 2)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(false, true, true, false, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(30.0, p.getObjective().get(0), 0.01);
		assertEquals(-6.83, p.getObjective().get(1), 0.01);
	}

	@Test
	public void testEvaluateFunctionOverload() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 1, 3, 2)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(true, true, true, false, true, true)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(0.0, p.getObjective().get(1), 0.01);
	}

	@Test
	public void testSimpleExample() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 3, 2, 1)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(20.0, p.getObjective().get(0), 0.01);
		assertEquals(0.0, p.getObjective().get(1), 0.01);
	}

}
