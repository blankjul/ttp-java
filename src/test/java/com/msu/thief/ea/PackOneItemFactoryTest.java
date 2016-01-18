package com.msu.thief.ea;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.thief.ea.factory.PackOneItemFactory;
import com.msu.thief.problems.variable.Pack;

public class PackOneItemFactoryTest extends Operator {
	
	
	@Test
	public void testFactoryAll() {
		PackOneItemFactory fac = new PackOneItemFactory(thief);
		List<Pack> pool = new ArrayList<>();
		while(fac.hasNext()) {
			pool.add(fac.next(rand));
		}
		assertEquals(4, pool.size());
	}
	
	
	@Test
	public void testFactoryTwoItems() {
		PackOneItemFactory fac = new PackOneItemFactory(thief, Arrays.asList(1,2));
		List<Pack> pool = new ArrayList<>();
		while(fac.hasNext()) {
			pool.add(fac.next(rand));
		}
		assertEquals(2, pool.size());
	}
	
	
	
	

}
