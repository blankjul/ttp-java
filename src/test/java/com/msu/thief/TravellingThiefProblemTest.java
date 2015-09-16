package com.msu.thief;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.exception.EvaluationException;
import com.msu.moo.util.Pair;
import com.msu.thief.model.packing.BooleanPackingList;
import com.msu.thief.model.packing.PackingList;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.thief.scenarios.impl.BonyadiScenario;
import com.msu.thief.variable.TTPVariable;

public class TravellingThiefProblemTest {

	private TravellingThiefProblem ttp;

	@Before
	public void setUp() {
		ttp = new BonyadiScenario().getObject();
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


}
