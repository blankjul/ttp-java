package com.msu.knp.model.factory;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.msu.scenarios.BonyadiPublicationScenario;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.variable.tour.Tour;
import com.msu.thief.variable.tour.factory.NearestNeighbourFactory;

public class NearestNeighbourFactoryTest {
	
	@Test
	public void testCorrectResultOnPublicationStarting0() {
		AbstractThiefProblem ttp = new BonyadiPublicationScenario().getObject();
		Tour<?> t = NearestNeighbourFactory.create(0, ttp.getMap());
		assertEquals(Arrays.asList(0,3,1,2), t.encode());
	}

	
	@Test
	public void testCorrectResultOnPublicationStarting2() {
		AbstractThiefProblem ttp = new BonyadiPublicationScenario().getObject();
		ttp.setStartingCityIsZero(false);
		Tour<?> t = NearestNeighbourFactory.create(2, ttp.getMap());
		assertEquals(Arrays.asList(2,1,0,3), t.encode());
	}
	
}
