package com.msu.thief.problem.variable;

import static org.junit.Assert.*;

import org.junit.Test;

import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;


public class TTPVariableTest {
	
	@Test
	public void testEqalVariableTrue() {
		TTPVariable var1 = TTPVariable.createFromString("0,1,2,3;0,0,1,0");
		TTPVariable var2 = TTPVariable.createFromString("0,1,2,3;0,0,1,0");
		assertEquals(var1, var2);
		assertEquals(var1.hashCode(), var2.hashCode());
	}
	
	
	@Test
	public void testEqalVariableFalse() {
		TTPVariable var1 = TTPVariable.createFromString("0,1,2,3;0,0,1,0");
		TTPVariable var2 = TTPVariable.createFromString("0,1,2,3;0,0,1,1");
		assertFalse(var1.equals(var2));
		assertTrue(var1.hashCode() != var2.hashCode());
	}
	
	
	
	
	@Test
	public void testParseFromString() {
		TTPVariable var = TTPVariable.createFromString("0,1,2,3;0,0,1,0");
		assertEquals(Tour.createFromString("0,1,2,3"), var.getTour());
		assertEquals(Pack.createFromBooleanString("0,0,1,0"), var.getPack());
	}
	
	

	
	
	
	
}
