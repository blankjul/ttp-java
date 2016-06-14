package com.msu.thief.problem.variable;


import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.msu.thief.problems.variable.Tour;



public class TourTest {
	
	@Test
	public void testCreateFromString() {
		Tour t = Tour.createFromString("[0,1,2,3]");
		assertEquals(Arrays.asList(0,1,2,3), t.decode());
	}
	
	@Test
	public void testCreateFromStringWithWhitespace() {
		Tour t = Tour.createFromString("[0, 1, 2, 3]");
		assertEquals(Arrays.asList(0,1,2,3), t.decode());
	}
	
	@Test
	public void testCreateFromStringNoBrackets() {
		Tour t = Tour.createFromString("0, 1, 2, 3");
		assertEquals(Arrays.asList(0,1,2,3), t.decode());
	}
	
	@Test
	public void testSymmetric() {
		Tour t = Tour.createFromString("[0,1,2,3]");
		assertEquals(Arrays.asList(0,3,2,1), t.getSymmetric().decode());
		assertEquals(Arrays.asList(0,1,2,3), t.decode());
	}
	
	
	@Test
	public void testStartsWithZero() {
		assertEquals(true, Tour.createFromString("[0,1,2,3]").startsWithZero());
		assertEquals(false, Tour.createFromString("[1,1,2,3]").startsWithZero());
	}

	
	@Test
	public void testIsPermutation() {
		assertEquals(true, Tour.createFromString("[0,1,2,3]").isPermutation());
		assertEquals(false, Tour.createFromString("[1,1,2,3]").isPermutation());
		assertEquals(false, Tour.createFromString("[0,1,2,4]").isPermutation());
	}
	
	
	@Test
	public void testEquals() {
		assertEquals(true, Tour.createFromString("[0,1,2,3]").equals(Tour.createFromString("[0,1,2,3]")));
		assertEquals(false, Tour.createFromString("[0,2,2,3]").equals(Tour.createFromString("[0,1,2,3]")));
	}
	
	@Test
	public void testHashCode() {
		assertTrue(Tour.createFromString("[0,1,2,3]").hashCode() == Tour.createFromString("[0,1,2,3]").hashCode());
		assertFalse(Tour.createFromString("[2,1,2,3]").hashCode() == Tour.createFromString("[0,1,2,3]").hashCode());
	}
	
}
