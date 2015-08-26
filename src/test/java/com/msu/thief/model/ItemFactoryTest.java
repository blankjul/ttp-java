package com.msu.thief.model;

import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Test;

import com.msu.thief.factory.ItemFactory;
import com.msu.thief.model.Item;



public class ItemFactoryTest extends TestCase {



	@Test
	public void testItemListHasCorrectSize() {
		assertEquals(10, ItemFactory.create(ItemFactory.TYPE.UNCORRELATED, 10, 100).size());
	}


	@Test
	public void testItemWeakCorrelation() {
		Collection<Item> c = ItemFactory.create(ItemFactory.TYPE.WEAKLY_CORRELATED, 10, 10000);
		for(Item i : c) {
			assertTrue(i.getProfit() <= i.getWeight() + 1000 && i.getProfit() >= i.getWeight() - 1000);
		}
	}
	
	@Test
	public void testItemStrongCorrelation() {
		Collection<Item> c = ItemFactory.create(ItemFactory.TYPE.STRONGLY_CORRELATED, 10, 10000);
		for(Item i : c) {
			assertTrue(i.getProfit() <= i.getWeight() + 100 && i.getProfit() >= i.getWeight() - 100);
		}
	}

	
}
