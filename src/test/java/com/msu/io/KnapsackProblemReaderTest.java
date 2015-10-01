package com.msu.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.msu.io.reader.KnapsackProblemReader;
import com.msu.knp.KnapsackProblem;

public class KnapsackProblemReaderTest {

	protected static KnapsackProblem problem;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		KnapsackProblemReader reader = new KnapsackProblemReader();
		problem = reader.read("resources/knapPI_13_0020_1000.csv");
	}
	
	@Test
	public void testResultIsNotNull() {
		assertNotNull(problem);
	}
	
	@Test
	public void testNumberOfItems() {
		assertEquals(20, problem.numOfItems());
	}
	
	@Test
	public void testMaximalWeight() {
		assertEquals(873, problem.getMaxWeight());
	}
	
	@Test
	public void testOptimum() {
		assertEquals(1716, problem.getOptimum().get(0).getObjectives(0), 0.01);
	}
	
	@Test
	public void testFirstAndLastItemCorrect() {
		assertEquals(234, problem.getItems().get(0).getProfit(), 0.01);
		assertEquals(114, problem.getItems().get(0).getWeight(), 0.01);
		
		assertEquals(1053, problem.getItems().get(19).getProfit(), 0.01);
		assertEquals(873, problem.getItems().get(19).getWeight(), 0.01);
	}
	
	
	@Test (expected=RuntimeException.class) 
	public void testWrongPathToRead() {
		new KnapsackProblemReader().read("resources/4564645645.csv");
	}
	
	
}
