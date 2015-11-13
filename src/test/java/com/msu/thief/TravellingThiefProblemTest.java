package com.msu.thief;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.msu.evolving.ThiefProblemVariable;
import com.msu.io.reader.JsonThiefProblemReader;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.problems.ThiefProblem;
import com.msu.scenarios.PublicationScenario;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Pair;
import com.msu.util.exceptions.EvaluationException;

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
		ttp = new JsonThiefProblemReader().read("resources/example_symmetric_different.ttp");
		Solution s1 = ttp.evaluate(new TTPVariable("[0, 4, 2, 3, 1];[0, 0, 0, 0, 0]"));
		Solution s2 = ttp.evaluate(new TTPVariable("[0, 1, 3, 2, 4];[0, 0, 0, 0, 0]"));
		assertEquals(s1.getObjective(), s2.getObjective());
	}
	
	@Test 
	public void testEqualVariable() {
		TTPVariable  t1 = new TTPVariable("[0, 5, 4, 3, 2, 1];[0, 0, 0, 0, 0, 0]");
		TTPVariable  t2 = new TTPVariable("[0, 5, 4, 3, 2, 1];[0, 0, 0, 0, 0, 0]");
		assertTrue(t1.equals(t2));
	}
	
	@Test 
	public void testEqualVariableInsertIntoNonDominatedSet() {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		set.add(new Solution( new TTPVariable("[0, 5, 4, 3, 2, 1];[0, 0, 0, 0, 0, 0]") , Arrays.asList(5.0)));
		set.add(new Solution( new TTPVariable("[0, 5, 4, 3, 2, 1];[0, 0, 0, 0, 0, 0]") , Arrays.asList(5.0)));
		assertEquals(1, set.size());
	}
	
	
	
	@Test 
	public void testProblemIsEqual() {
		ThiefProblemVariable var1 = new ThiefProblemVariable(new JsonThiefProblemReader().read("resources/example_symmetric_different.ttp"));
		ThiefProblemVariable var2 = new ThiefProblemVariable(new JsonThiefProblemReader().read("resources/example_symmetric_different.ttp"));
		assertEquals(var1,var2);
	}
	
	@Test 
	public void testProblemNotEqual() {
		ThiefProblemVariable var1 = new ThiefProblemVariable(new JsonThiefProblemReader().read("resources/example_symmetric_different.ttp"));
		ThiefProblemVariable var2 = new ThiefProblemVariable(new JsonThiefProblemReader().read("resources/bonyadi_single_publication.ttp"));
		assertFalse(var1.equals(var2));
	}
	
	


}
