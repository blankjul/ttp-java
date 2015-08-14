package com.moo.ttp.operators.mutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.moo.operators.mutation.BitFlipMutation;

public class BitFlipMutationTest {
	
	BitFlipMutation bitMutation = new BitFlipMutation();

	private ArrayList<Boolean> l;
	private ArrayList<Boolean> org;

	@Before
	public void setUp() {
		l = new ArrayList<>(Arrays.asList(false, false, false, true));
		org = new ArrayList<>(l);
	}

	@Test
	public void testFlipAllBits() {
		bitMutation = new BitFlipMutation(1.0);
		bitMutation.mutate(l);
		assertEquals(l, new ArrayList<>(Arrays.asList(true, true, true, false)));
	}
	

	
	@Test
	public void testLargeArrayFlipAtLeastOne() {
		bitMutation = new BitFlipMutation(0.2);
		l = new ArrayList<>();
		for (int i = 0; i < 2000; i++) {
			l.add(false);
		}
		org = new ArrayList<>(l);
		bitMutation.mutate(l);
		assertNotEquals(l, org);
	}
	
	
	

}
