package com.msu.thief;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.problems.SingleObjectiveThiefProblem;
import com.msu.scenarios.PublicationScenarioSingleObjective;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;

public class SingleObjectiveTravellingThiefProblemBonyadiCorrectnessTest {

	@Test
	public void testEvaluateFunctionOneValue() {
		SingleObjectiveThiefProblem ttp = new PublicationScenarioSingleObjective().getObject();
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 2, 1, 3)));
		PackingList<List<Boolean>> b = new BooleanPackingList(new ArrayList<Boolean>(Arrays.asList(false, true, false, true, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(73.14, p.getObjective().get(0), 0.01);
	}

	@Test
	public void testEvaluateFunctionBonyadiOptimum() {
		SingleObjectiveThiefProblem ttp = new PublicationScenarioSingleObjective().getObject();
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(0, 1, 3, 2)));
		PackingList<List<Boolean>> b = new BooleanPackingList(new ArrayList<Boolean>(Arrays.asList(false, true, true, false, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(-50.0, p.getObjective().get(0), 0.01);
	}



}
