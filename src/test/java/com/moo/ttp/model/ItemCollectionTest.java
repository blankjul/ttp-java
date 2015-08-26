package com.moo.ttp.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.msu.thief.model.Item;
import com.msu.thief.model.ItemCollection;



public class ItemCollectionTest  {

	ItemCollection<Item> collection = new ItemCollection<Item>();
	
	@Before
    public void setUp() {
    }
	
	@Test
	public void testAddItemToCollectionSuccessfull() {
		collection.add(0, new Item(1, 1));
		assertEquals(1, collection.getItemsFromCity(0).size());
	}

	
	public void testGetItemsButNoOneAtCity()  {
		assertEquals(1, collection.getItemsFromCity(0).size());
	}
	


	
}
