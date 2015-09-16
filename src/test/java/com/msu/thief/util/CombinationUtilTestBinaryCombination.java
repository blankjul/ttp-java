package com.msu.thief.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.msu.util.CombinatorialUtil;

@RunWith(value = Parameterized.class)
public class CombinationUtilTestBinaryCombination {

	protected int n;

	public CombinationUtilTestBinaryCombination(int n) {
		super();
		this.n = n;
	}

	@Parameters(name = "length {0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { { 0 }, { 1 }, { 4 }, { 10 } });
	}

	@Test
	public void testBinaryCombination() {
		assertEquals(Math.pow(2, n), CombinatorialUtil.getAllBooleanCombinations(n).size(), 0.01);
	}

}
