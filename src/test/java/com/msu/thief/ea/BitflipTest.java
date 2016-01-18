package com.msu.thief.ea;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.msu.thief.ea.operators.PackBitflipMutation;
import com.msu.thief.problems.variable.Pack;

public class BitflipTest extends Operator{
	
	
	@Test
	public void testEqalVariableFalse() {
		Pack p = Pack.createFromString("[0,1,2,3]");
		Pack org = p.copy();
		new PackBitflipMutation(thief).mutate(p, rand);
		assertTrue(!org.equals(p));
	}
	
	
	

}
