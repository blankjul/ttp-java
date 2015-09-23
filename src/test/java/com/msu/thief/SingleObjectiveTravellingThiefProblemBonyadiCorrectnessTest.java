package com.msu.thief;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.PackingList;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Pair;
import com.msu.scenarios.thief.bonyadi.PublicationScenarioSingleObjective;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;

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

	@Test
	public void testEvaluateFunctionNewOptimum() {
		SingleObjectiveThiefProblem ttp = new PublicationScenarioSingleObjective().getObject();
		Tour<List<Integer>> tour = new StandardTour(new ArrayList<Integer>(Arrays.asList(3, 0, 1, 2)));
		PackingList<List<Boolean>> b = new BooleanPackingList(new ArrayList<Boolean>(Arrays.asList(false, true, true, false, false, false)));
		Solution p = ttp.evaluate(new TTPVariable(Pair.create(tour, b)));
		assertEquals(-54.0, p.getObjective().get(0), 0.01);
	}

	// [3, 0, 1, 2];[0, 1, 1, 0, 0, 0]

}
