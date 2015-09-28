package com.msu.scenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.msu.scenarios.thief.bonyadi.BenchmarkMuliObjective;
import com.msu.thief.ThiefProblem;

public class BenchmarkMuliObjectiveTest {
	
	protected static ThiefProblem ttp;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		BenchmarkMuliObjective fac = new BenchmarkMuliObjective();
		ttp = fac.create("../ttp-benchmark/MultiObjective/10/10_3_1_25.txt");
	}
	
	
	@Test
	public void testNumOfCitiesCorrect() {
		assertEquals(10, ttp.numOfCities());
	}
	
	
	@Test
	public void testMaxWeight() {
		assertEquals(262, ttp.getMaxWeight());
	}
	
	@Test
	public void testDistancesNotZero() {
		assertTrue(0 != ttp.getMap().get(0, 1));
	}
	
	@Test
	public void testHasItems() {
		assertTrue(ttp.getItemCollection().size() != 0);
	}
	

}
