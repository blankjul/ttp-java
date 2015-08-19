package com.moo.mutation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.moo.operators.mutation.SwapMutation;

public class SwapMutationTest {
	
	SwapMutation<List<Integer>> swapMutation = new SwapMutation<>();

	private ArrayList<Integer> l;
	private ArrayList<Integer> org;

	@Before
	public void setUp() {
		l = new ArrayList<>(Arrays.asList(0,1,2,3,4));
		org = new ArrayList<>(l);
	}

	@Test
	public void testSwapByPositions() {
		for (int i = 0; i < 20; i++) swapMutation.mutate(l);
		assertNotEquals(l, org);
	}
	
	@Test
	public void testSwapNotTheFirstPosition() {
		swapMutation = new SwapMutation<>(1);
		for (int i = 0; i < 20; i++) {
			l = new ArrayList<>(org);
			swapMutation.mutate(l);
			assertEquals(l.get(0), new Integer(0));
		}

	}
	
	

}
