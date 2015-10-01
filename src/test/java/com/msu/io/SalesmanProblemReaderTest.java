package com.msu.io;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.msu.io.reader.SalesmanProblemReader;
import com.msu.tsp.TravellingSalesmanProblem;

public class SalesmanProblemReaderTest {

	protected static TravellingSalesmanProblem problem;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		SalesmanProblemReader reader = new SalesmanProblemReader();
		problem = reader.read("resources/berlin52.tsp");
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
	public void testOptimum() {
		assertEquals(7542, problem.getOptimum().get(0).getObjectives(0),0.01);
	}
	
	
	
	
	
}
