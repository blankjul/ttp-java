package com.msu.thief.model.tour;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;

public class StandardTourTest {
	
	@Test
	public void testCreateFromString() {
		Tour<?> t = new StandardTour("[0,1,2,3]");
		assertEquals(Arrays.asList(0,1,2,3), t.encode());
	}
	
	@Test
	public void testCreateFromStringWithWhitespace() {
		Tour<?> t = new StandardTour("[0, 1, 2, 3]");
		assertEquals(Arrays.asList(0,1,2,3), t.encode());
	}
	
	@Test
	public void testCreateFromStringNoBrackets() {
		Tour<?> t = new StandardTour("0, 1, 2, 3");
		assertEquals(Arrays.asList(0,1,2,3), t.encode());
	}
	
	@Test
	public void testSymmetric() {
		Tour<?> t = new StandardTour("[0,1,2,3]");
		assertEquals(Arrays.asList(0,3,2,1), t.getSymmetric().encode());
		assertEquals(Arrays.asList(0,1,2,3), t.encode());
	}

}
