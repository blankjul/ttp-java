package com.msu.thief.ea;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.thief.ea.pack.factory.ThiefPackOneItemFactory;
import com.msu.thief.problems.variable.Pack;

public class PackOneItemFactoryTest extends Operator {
	
	
	@Test
	public void testFactoryAll() {
		ThiefPackOneItemFactory fac = new ThiefPackOneItemFactory();
		fac.initialize(thief, rand);
		List<Pack> pool = new ArrayList<>();
		while(fac.hasNext()) {
			pool.add(fac.create());
		}
		assertEquals(4, pool.size());
	}
	
	
	@Test
	public void testFactoryTwoItems() {
		ThiefPackOneItemFactory fac = new ThiefPackOneItemFactory();
		fac.initialize(thief, rand, Arrays.asList(1,2));
		List<Pack> pool = new ArrayList<>();
		while(fac.hasNext()) {
			pool.add(fac.create());
		}
		assertEquals(2, pool.size());
	}
	
	
	
	

}
