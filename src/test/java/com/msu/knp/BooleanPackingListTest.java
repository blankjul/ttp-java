package com.msu.knp;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.PackingList;

public class BooleanPackingListTest {

	@Test
	public void testCreateFromStringInteger() {
		PackingList<?> l = new BooleanPackingList("[0,0,1]");
		assertEquals(Arrays.asList(false, false, true), l.encode());
	}
	
	@Test
	public void testCreateFromStringBoolean() {
		PackingList<?> l = new BooleanPackingList("[false,false,true]");
		assertEquals(Arrays.asList(false, false, true), l.encode());
	}
	
	
}
