package com.msu.thief.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.msu.thief.evaluator.profit.ExponentialProfitEvaluator;
import com.msu.thief.io.thief.reader.ThiefMultiObjectiveReader;
import com.msu.thief.problems.AbstractThiefProblem;

public class ThiefMultiObjectiveReaderTest {

	protected static AbstractThiefProblem problem;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
		ThiefMultiObjectiveReader reader = new ThiefMultiObjectiveReader();
		problem = reader.read("resources/TTP_MO_10_3_1_25.txt");
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
	public void testDroppingExpontential() {
		assertTrue(problem.getProfitEvaluator() instanceof ExponentialProfitEvaluator);
		ExponentialProfitEvaluator exp = (ExponentialProfitEvaluator) problem.getProfitEvaluator();
		assertEquals(6.397517, exp.getDroppingConstant(), 0.01);
		assertEquals(0.836392, exp.getDroppingRate(), 0.01);
	}
	

	@Test
	public void testDistanceFirstAndSecond() {
		assertEquals(3.9020, problem.getMap().get(0, 1), 0.01);
	}
	
	@Test
	public void testNumberOfItems() {
		assertEquals(16, problem.numOfItems());
	}
	
	
	@Test
	public void testName() {
		assertEquals("TTP_MO_10_3_1_25", problem.toString());
	}
	
	
}
