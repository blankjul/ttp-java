package com.msu.scenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.msu.scenarios.thief.bonyadi.BenchmarkTSPLIB;
import com.msu.thief.ThiefProblem;

public class BenchmarkTSPLIBTest {
	
	protected static ThiefProblem ttp;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		BenchmarkTSPLIB fac = new BenchmarkTSPLIB();
		ttp = fac.create("../ttp-benchmark/TSPLIB/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");
		System.out.println("");
	}
	
	
	@Test
	public void testNumOfCitiesCorrect() {
		assertEquals(52, ttp.numOfCities());
	}
	
	
	@Test
	public void testMaxWeight() {
		assertEquals(4046, ttp.getMaxWeight());
	}
	
	@Test
	public void testDistancesNotZero() {
		assertTrue(0 != ttp.getMap().get(0, 1));
	}
	
	@Test
	public void testHasItems() {
		assertTrue(ttp.getItems().size() != 0);
	}
	

}
