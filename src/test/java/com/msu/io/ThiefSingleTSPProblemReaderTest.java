package com.msu.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.msu.thief.io.thief.reader.ThiefSingleTSPLIBProblemReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;

public class ThiefSingleTSPProblemReaderTest {

	protected static SingleObjectiveThiefProblem problem;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		ThiefSingleTSPLIBProblemReader reader = new ThiefSingleTSPLIBProblemReader();
		problem = reader.read("resources/TTP_TSPLIB_berlin52_n51_bounded-strongly-corr_01.ttp");
	}
	
	@Test
	public void testResultIsNotNull() {
		assertNotNull(problem);
	}
	
	@Test
	public void testNumOfCities() {
		assertEquals(52, problem.numOfCities());
	}
	
	
	@Test
	public void testDistanceBetweenFirstAndSecondCorrect() {
		assertEquals(666, problem.getMap().get(0, 1),0.01);
	}
	
	@Test
	public void testVelocity() {
		assertEquals(0.1, problem.getMinSpeed(), 0.01);
		assertEquals(1, problem.getMaxSpeed(), 0.01);
	}
	
	@Test
	public void testR() {
		assertEquals(0.31, problem.getR(), 0.01);
	}
	@Test
	public void testMaximalWeight() {
		assertEquals(4046, problem.getMaxWeight());
	}
	
	
	
}
