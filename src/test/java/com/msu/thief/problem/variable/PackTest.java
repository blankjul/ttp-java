package com.msu.thief.problem.variable;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import com.msu.thief.exceptions.VariableNotValidException;
import com.msu.thief.problems.variable.Pack;


public class PackTest {
	
	
	@Test
	public void testParseFromString() {
		assertEquals(new HashSet<>(Arrays.asList(1,2,3)), Pack.createFromString("[1,1,2,3]").encode());
	}
	
	
	@Test
	public void testNotPickedUp() {
		Pack p = Pack.createFromString("[0,1,2,3]");
		assertEquals(new HashSet<>(Arrays.asList(4)), p.getNotPickedItems(5));
	}
	

	
	@Test
	public void testAnyPickedUp() {
		Pack p = Pack.createFromString("[]");
		assertFalse(p.isAnyPicked());
	}
	
	@Test
	public void testParseFromBooleanString() {
		assertEquals(new HashSet<>(Arrays.asList(1,2,3)), Pack.createFromBooleanString("[0,1,1,1]").encode());
	}
	
	
	@Test(expected = VariableNotValidException.class)  
	public void testValidate() {
		Pack.createFromString("[10]").validate(10);
	}
	
	
	@Test
	public void testEquals() {
		assertEquals(Pack.createFromString("[0,1,2,3]"), Pack.createFromString("[0,1,2,3]"));
		assertFalse(Pack.createFromString("[0,1,2,3]").equals(Pack.createFromString("[0,1,2,4]")));
	}
	
	
	
}
