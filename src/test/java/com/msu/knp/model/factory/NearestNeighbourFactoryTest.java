package com.msu.knp.model.factory;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.scenarios.impl.PublicationScenario;
import com.msu.tsp.model.Tour;
import com.msu.tsp.model.factory.NearestNeighbourFactory;

public class NearestNeighbourFactoryTest {
	
	@Test
	public void testCorrectResultOnPublicationStarting0() {
		TravellingThiefProblem ttp = new PublicationScenario().getObject();
		Tour<?> t = NearestNeighbourFactory.create(0, ttp.getMap());
		assertEquals(Arrays.asList(0,3,1,2), t.encode());
	}

	
	@Test
	public void testCorrectResultOnPublicationStarting2() {
		TravellingThiefProblem ttp = new PublicationScenario().getObject();
		Tour<?> t = NearestNeighbourFactory.create(2, ttp.getMap());
		assertEquals(Arrays.asList(2,1,0,3), t.encode());
	}
	
}
