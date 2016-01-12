package com.msu.thief.ea;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.msu.thief.ea.pack.mutation.BitflipMutation;
import com.msu.thief.problems.variable.Pack;

public class BitflipTest extends Operator{
	
	
	@Test
	public void testEqalVariableFalse() {
		Pack p = Pack.createFromString("[0,1,2,3]");
		Pack org = p.copy();
		new BitflipMutation().mutate(thief, rand, p);
		assertTrue(!org.equals(p));
	}
	
	
	

}
