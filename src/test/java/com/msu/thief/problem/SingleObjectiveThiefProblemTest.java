package com.msu.thief.problem;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;

public class SingleObjectiveThiefProblemTest {

	
	private SingleObjectiveThiefProblem ttp;

	@Before
	public void setUp() {
		ttp = PublicationScenario.getExampleSingleObjective();
	}
	
	@Test
	public void testEvaluateFunctionOneValue() {
		TTPVariable var = TTPVariable.createFromString("0,2,1,3; 0,1,0,1,0,0");
		assertEquals(73.14, ttp.evaluate(var).getObjective(0), 0.01);
	}

	@Test
	public void testEvaluateFunctionBonyadiOptimum() {
		TTPVariable var = TTPVariable.createFromString("0, 1, 3, 2; 0,1,1,0,0,0");
		assertEquals(-50.0, ttp.evaluate(var).getObjective(0), 0.01);
	}

	@Test
	public void testEvaluateFunctionBonyadiOverweight() {
		TTPVariable var = TTPVariable.createFromString("0, 1, 3, 2; 1,1,1,1,1,1");
		assertEquals(9.0, ttp.evaluate(var).getConstraintViolations().get(0), 0.01);
	}
	
	
}
