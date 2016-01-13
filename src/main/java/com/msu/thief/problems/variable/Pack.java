package com.msu.thief.problems.variable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.interfaces.IVariable;
import com.msu.thief.exceptions.VariableNotValidException;
import com.msu.thief.util.StringUtil;

/**
 * This class represents the packing list. All indices of items which are
 * picked up are saved in a HashSet.
 * 
 * This allows to return all items in O(1), but has worse runtime performance 
 * in comparison to a boolean vector.
 * 
 */
public class Pack implements IVariable{

	
	//! all items which are picked up
	protected Set<Integer> items;
	
	
	/**
	 * Initialize an empty packing list
	 */
	public Pack() {
		this.items = new HashSet<>();
	}
	
	/**
	 * Initialize a packing list from a collections of inidixes
	 */
	public Pack(Collection<Integer> c) {
		this.items = new HashSet<>(c);
	}
	
	/**
	 * Initialize a packing list from a boolean list
	 */
	public Pack(List<Boolean> b) {
		this();
		for (int i = 0; i < b.size(); i++) {
			if (b.get(i)) items.add(i);
		}
	}

	
	/**
	 * Initialize a packing list with one item
	 */
	public Pack(int idx) {
		this(Arrays.asList(idx));
	}
	
	/**
	 * ads an item to the packing list
	 * @param idx index to add
	 * @return true if successfully added.
	 */
	public boolean add(int idx) {
		return items.add(idx);
	}
	
	
	/**
	 * removes an item from the packing list
	 * @param idx index to remove
	 * @return true if successfully removed.
	 */
	public boolean remove(int idx) {
		return items.remove(idx);
	}
	
	
	/**
	 * copy the set of items
	 * @return
	 */
	public Pack copy() {
		return new Pack(items);
	}
	

	@Override
	public String toString() {
		return Arrays.toString(items.toArray());
	}

	/**
	 * @param idx of item
	 * @return if item is picked or not
	 */
	public boolean isPicked(int idx) {
		return items.contains(idx);
	}

	/**
	 * @return true if at least one item is picked
	 */
	public boolean isAnyPicked() {
		return !items.isEmpty();
	}

	
	/**
	 * @param numOfItems number of items provided by the problem.
	 * @return all items which are not picked.
	 */
	public Set<Integer> getNotPickedItems(int numOfItems) {
		Set<Integer> hash = new HashSet<>();
		for (int i = 0; i < numOfItems; i++) {
			if (!items.contains(i))
				hash.add(i);
		}
		return hash;
	}
	
	
	/**
	 * @return set of integer
	 */
	public Set<Integer> encode() {
		return items;
	}
	
	
	/**
	 * Checks whether the pack plan is valid. 
	 * No items indices which are larger than the number of items are allowed.
	 */
	public void validate(int numOfItems) {

		for(int idx : items) {
			// it is equal because there is an item 0
			if (idx >= numOfItems) throw new VariableNotValidException(String
					.format("There are only %s items for the problem, but item %s is picked.", numOfItems, idx));
		}
	}
	
	
	/**
	 * @return number of items that are picked up
	 */
	public int numOfItems() {
		return items.size();
	}
	
	
	public static Pack createFromString(String s) {
		return new Pack(StringUtil.parseAsIntegerList(s));
	}
	
	public static Pack createFromBooleanString(String s) {
		return new Pack(StringUtil.parseAsBooleanList(s));
	}

	
	
	@Override
	public int hashCode() {
		return items.hashCode();
	}

	
	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject)
			return true;
		if (getClass() != otherObject.getClass())
			return false;
		Pack other = (Pack) otherObject;
		return items.equals(other.items);
	}

	
	
	
	// TODO: fix inheritance
	
	
	@Override
	public Object get() {
		return null;
	}

	@Override
	public void set(Object obj) {
		
	}

	@Override
	public <V extends IVariable> V cast(Class<V> clazz) {
		return null;
	}
	
	public static Pack empty() {
		return new Pack();
	}
	
	
}
