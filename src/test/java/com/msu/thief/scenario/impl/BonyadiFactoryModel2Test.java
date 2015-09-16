package com.msu.thief.scenario.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.msu.thief.TravellingThiefProblem;
import com.msu.thief.scenarios.impl.BonyadiFactoryModel1;

public class BonyadiFactoryModel2Test {
	
	protected TravellingThiefProblem ttp;
	
	
	@Before
    public void setUp() {
		BonyadiFactoryModel1 fac = new BonyadiFactoryModel1();
		ttp = fac.create("../ttp-bonyadi/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");
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
