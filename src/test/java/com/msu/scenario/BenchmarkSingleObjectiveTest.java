package com.msu.scenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.msu.scenarios.thief.bonyadi.BenchmarkSingleObjective;
import com.msu.thief.SingleObjectiveThiefProblem;

public class BenchmarkSingleObjectiveTest {
	
	protected static SingleObjectiveThiefProblem ttp;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		BenchmarkSingleObjective fac = new BenchmarkSingleObjective();
		ttp = fac.create("../ttp-benchmark/SingleObjective/10/10_3_1_25.txt");
	}
	
	@Test
	public void testNumOfCitiesCorrect() {
		assertEquals(10, ttp.numOfCities());
	}
	
	@Test
	public void testR() {
		assertEquals(13.670700, ttp.getR(), 0.01);
	}
	
	@Test
	public void testMaxWeight() {
		assertEquals(488, ttp.getMaxWeight());
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
