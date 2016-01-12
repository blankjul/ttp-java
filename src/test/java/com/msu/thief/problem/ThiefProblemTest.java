package com.msu.thief.problem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class ThiefProblemTest {

	private ThiefProblem ttp;

	@Before
	public void setUp() {
		ttp = PublicationScenario.getExampleMutliObjective();
	}

	@Test
	public void testEvaluateFunctionG3() {
		Solution s = ttp.evaluate(TTPVariable.createFromString("0,1,2,3; 0,0,0,0,0,0"));
		assertEquals(20.0, s.getObjective(0), 0.01);
		assertEquals(0.0, s.getObjective(1), 0.01);
	}
	
	@Test
	public void testEvaluateFunctionG2() {
		Solution s = ttp.evaluate(TTPVariable.createFromString("0, 1, 3, 2; 0,1,0,0,0,0"));
		assertEquals(23.57, s.getObjective(0), 0.01);
		assertEquals(-3.65, s.getObjective(1), 0.01);
	}
	
	@Test
	public void testEvaluateFunctionG1() {
		Solution s = ttp.evaluate(TTPVariable.createFromString("0, 1, 3, 2; 0,1,1,0,0,0"));
		assertEquals(30.0, s.getObjective(0), 0.01);
		assertEquals(-6.83, s.getObjective(1), 0.01);
	}
	
	@Test
	public void testEvaluateFunctionOverload() {
		Solution s = ttp.evaluate(TTPVariable.createFromString("0, 1, 3, 2; 1,1,1,0,1,1"));
		assertTrue(s.hasConstrainViolations());
	}
	
	@Test
	public void testSimpleExample2() {
		Solution s = ttp.evaluate(TTPVariable.createFromString("0,2,1,3; 0,1,0,1,0,0"));
		assertEquals(133.14, s.getObjective(0), 0.01);
		assertEquals(- 1.047 - 0.564, s.getObjective(1), 0.01);
	}


}
