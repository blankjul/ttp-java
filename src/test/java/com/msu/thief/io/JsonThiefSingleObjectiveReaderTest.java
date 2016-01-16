package com.msu.thief.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.msu.thief.io.thief.reader.ThiefSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;

public class JsonThiefSingleObjectiveReaderTest {

	protected static SingleObjectiveThiefProblem problem;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		ThiefSingleObjectiveReader reader = new ThiefSingleObjectiveReader();
		problem = reader.read("resources/TTP_SO_10_3_1_25.txt");
	}
	
	@Test
	public void testResultIsNotNull() {
		assertNotNull(problem);
	}
	

	@Test
	public void testNumOfCities() {
		assertEquals(10, problem.numOfCities());
	}
	
	@Test
	public void testVelocity() {
		assertEquals(0.1, problem.getMinSpeed(), 0.01);
		assertEquals(1, problem.getMaxSpeed(), 0.01);
	}
	
	@Test
	public void testR() {
		assertEquals(13.67, problem.getR(), 0.01);
	}

	@Test
	public void testDistanceFirstAndSecond() {
		assertEquals(3.3660, problem.getMap().get(0, 1), 0.01);
	}
	
	@Test
	public void testNumberOfItems() {
		assertEquals(19, problem.numOfItems());
	}
	
	@Test
	public void testName() {
		assertEquals("TTP_SO_10_3_1_25", problem.toString());
	}
	
	
	
	
	
	
}
