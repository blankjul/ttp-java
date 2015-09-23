package com.msu.thief;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.PackingList;
import com.msu.moo.util.Pair;
import com.msu.moo.util.exceptions.EvaluationException;
import com.msu.thief.scenarios.impl.BonyadiPublicationScenario;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;

public class TravellingThiefProblemTest {

	private TravellingThiefProblem ttp;

	@Before
	public void setUp() {
		ttp = new BonyadiPublicationScenario().getObject();
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
