package com.msu.ttp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.util.exceptions.EvaluationException;
import com.msu.thief.model.SymmetricMap;
import com.msu.tsp.TravellingSalesmanProblem;


public class TravellingSalesmanProblemTest {
	
	private TravellingSalesmanProblem tsp;
	
	@Before
	public void setUp() {
		SymmetricMap m = new SymmetricMap(3).set(0, 1, 1).set(1, 2, 2).set(2, 0, 3);
		tsp = new TravellingSalesmanProblem(m);
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
