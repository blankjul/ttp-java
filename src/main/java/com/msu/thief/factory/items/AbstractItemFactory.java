package com.msu.thief.factory.items;

import java.util.ArrayList;
import java.util.Collection;

import com.msu.thief.model.Item;

/**
 * Abstract factory for creating items
 *
 */
public abstract class AbstractItemFactory {

	public abstract Item create();
	
	public Collection<Item> create(int n) {
		ArrayList<Item> items = new ArrayList<Item>(n);
		while (items.size() < n) {
			Item i = create();
			items.add(i);
		}
		return items;
	}
	
}
