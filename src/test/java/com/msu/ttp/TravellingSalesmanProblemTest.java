package com.msu.ttp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.util.exceptions.EvaluationException;


public class TravellingSalesmanProblemTest {
	
	private SalesmanProblem tsp;
	
	@Before
	public void setUp() {
		SymmetricMap m = new SymmetricMap(3).set(0, 1, 1).set(1, 2, 2).set(2, 0, 3);
		tsp = new SalesmanProblem(m);
	}
	
	
	@Test
	public void testEvaluateFunction() {
		assertEquals(6.0, tsp.evaluate( new ArrayList<Integer>(Arrays.asList(0,1,2))), 0.01);
	}
	
	@Test (expected=EvaluationException.class) 
	public void testWrongSizeOfTour() throws RuntimeException {
		tsp.evaluate( new ArrayList<Integer>(Arrays.asList(0)));
	}
	
	@Test (expected=EvaluationException.class) 
	public void testNotAValidPermutation() throws RuntimeException {
		tsp.evaluate( new ArrayList<Integer>(Arrays.asList(0,0,1)));
	}
	
	@Test (expected=EvaluationException.class) 
	public void testValidPermuationButMissingCity() throws RuntimeException {
		tsp.evaluate( new ArrayList<Integer>(Arrays.asList(0,2,3)));
	}
	

}
