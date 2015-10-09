package com.msu.thief.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashMultimap;

/**
 * This class represents a ItemCollection that maps each item to a 
 * city. Several utility structures are used to provide fast access.
 * @param <T> Item class or inherited one!
 */
public class ItemCollection<T extends Item> implements Iterable<T> {

	// ! items as a vector list
	protected ArrayList<T> items;

	// ! map for assign each city all the item indices
	protected HashMultimap<Integer, Integer> mapFromCityToItem;

	// ! map for assign each item the city
	protected HashMap<Integer, Integer> mapFromItemToCity;

	
	public ItemCollection() {
		super();
		items = new ArrayList<T>();
		mapFromCityToItem = HashMultimap.create();
		mapFromItemToCity = new HashMap<Integer, Integer>();
	}


	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}
	
	
	/**
	 * Add an item to a city.
	 */
	public void add(int city, T item) {
		items.add(item);
		int index = items.size() - 1;
		mapFromCityToItem.put(city, index);
		mapFromItemToCity.put(index, city);
	}

	/**
	 * @return items as a vector
	 */
	public List<T> asList() {
		return items;
	}
	
	/**
	 * @return true if item is at the specific city, otherwise false
	 */
	public boolean isItemAtCity(int city, T item) {
		return mapFromItemToCity.get(item) == city;
	}
	
	/**
	 * @return all items that are at the specific city.
	 */
	public List<T> getItemsFromCity(int city) {
		List<T> result = new ArrayList<T>();
		for(Integer index : mapFromCityToItem.get(city)) {
			result.add(items.get(index));
		}
		return result;
	}
	
	/**
	 * @return all items that are at the specific city by index
	 */
	public Set<Integer> getItemsFromCityByIndex(int city) {
		return mapFromCityToItem.get(city);
	}
	
	public int size() {
		return items.size();
	}
	
	public Item get(int i) {
		return items.get(i);
	}
	
	

}
