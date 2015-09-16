package com.msu.thief.model;

import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Test;

import com.msu.knp.impl.scenarios.RandomKnapsackScenario;
import com.msu.thief.model.Item;



public class ItemFactoryTest extends TestCase {



	@Test
	public void testItemListHasCorrectSize() {
		assertEquals(10, new RandomKnapsackScenario(RandomKnapsackScenario.CORRELATION_TYPE.STRONGLY_CORRELATED, 10000).create(10).size());
	}


	@Test
	public void testItemWeakCorrelation() {
		Collection<Item> c = new RandomKnapsackScenario(RandomKnapsackScenario.CORRELATION_TYPE.WEAKLY_CORRELATED, 10000).create(10);
		for(Item i : c) {
			assertTrue(i.getProfit() <= i.getWeight() + 1000 && i.getProfit() >= i.getWeight() - 1000);
		}
	}
	
	@Test
	public void testItemStrongCorrelation() {
		Collection<Item> c = new RandomKnapsackScenario(RandomKnapsackScenario.CORRELATION_TYPE.STRONGLY_CORRELATED, 10000).create(10);
		for(Item i : c) {
			assertTrue(i.getProfit() <= i.getWeight() + 100 && i.getProfit() >= i.getWeight() - 100);
		}
	}

	
}
