package com.msu.thief;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.msu.io.reader.JsonThiefReader;
import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.PackingList;
import com.msu.meta.FactoryThiefVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.moo.util.exceptions.EvaluationException;
import com.msu.scenarios.thief.bonyadi.PublicationScenario;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;

public class TravellingThiefProblemTest {

	private ThiefProblem ttp;

	@Before
	public void setUp() {
		ttp = new PublicationScenario().getObject();
	}

	
	@Test (expected=EvaluationException.class) 
	public void testPickingVectorWrongLength() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 3, 2, 1)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(false, false)));
		ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
	}
	
	@Test (expected=EvaluationException.class) 
	public void testWrongSizeTour() {
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 3, 2)));
		PackingList<List<Boolean>> b = new BooleanPackingList(
				new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false)));
		ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
	}
	
	@Test 
	public void testSymmetricTourDifferentObjectives() {
		ttp = new JsonThiefReader().read("resources/example_symmetric_different.ttp");
		Solution s1 = ttp.evaluate(new TTPVariable("[0, 4, 2, 3, 1];[0, 0, 0, 0, 0]"));
		Solution s2 = ttp.evaluate(new TTPVariable("[0, 1, 3, 2, 4];[0, 0, 0, 0, 0]"));
		assertEquals(s1.getObjective(), s2.getObjective());
	}
	
	@Test 
	public void testProblemIsEqual() {
		FactoryThiefVariable var1 = new FactoryThiefVariable(new JsonThiefReader().read("resources/example_symmetric_different.ttp"));
		FactoryThiefVariable var2 = new FactoryThiefVariable(new JsonThiefReader().read("resources/example_symmetric_different.ttp"));
		assertEquals(var1,var2);
	}
	
	@Test 
	public void testProblemNotEqual() {
		FactoryThiefVariable var1 = new FactoryThiefVariable(new JsonThiefReader().read("resources/example_symmetric_different.ttp"));
		FactoryThiefVariable var2 = new FactoryThiefVariable(new JsonThiefReader().read("resources/bonyadi_single_publication.ttp"));
		assertFalse(var1.equals(var2));
	}
	


}
