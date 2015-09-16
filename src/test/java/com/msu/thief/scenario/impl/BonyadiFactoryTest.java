package com.msu.thief.scenario.impl;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.scenarios.impl.BonyadiFactory;

public class BonyadiFactoryTest {
	
	protected TravellingThiefProblem ttp;
	
	
	@Before
    public void setUp() {
		BasicConfigurator.configure();
		BonyadiFactory fac = new BonyadiFactory();
		ttp = fac.create("../ttp-benchmark/10/10_3_1_25.txt");
    }
	
	
	@Test
	public void testNumOfCitiesCorrect() {
		assertEquals(10, ttp.numOfCities());
	}
	
	/*
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
	*/

}
