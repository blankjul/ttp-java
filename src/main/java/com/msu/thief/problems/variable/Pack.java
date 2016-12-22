package com.msu.thief.problems.variable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.moo.interfaces.IEvolutionaryVariable;
import com.msu.moo.model.variable.AVariable;
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
public class Pack extends AVariable<Set<Integer>> implements IEvolutionaryVariable<Set<Integer>> {

	
	
	/**
	 * Initialize an empty packing list
	 */
	public Pack() {
		this(new HashSet<>());
	}
	
	/**
	 * Initialize a packing list from a set of indixes
	 */
	public Pack(Set<Integer> c) {
		super(c);
	}
	
	/**
	 * Initialize a packing list from a collections of indixes
	 */
	public Pack(Collection<Integer> c) {
		super(new HashSet<>(c));
	}
	
	/**
	 * Initialize a packing list from a boolean list
	 */
	public Pack(List<Boolean> b) {
		super(new HashSet<>());
		for (int i = 0; i < b.size(); i++) {
			if (b.get(i)) obj.add(i);
		}
	}

	
	/**
	 * Initialize a packing list with one item
	 */
	public Pack(int idx) {
		super(new HashSet<>());
		obj.add(idx);
	}
	
	/**
	 * ads an item to the packing list
	 * @param idx index to add
	 * @return true if successfully added.
	 */
	public boolean add(int idx) {
		return obj.add(idx);
	}
	
	
	/**
	 * removes an item from the packing list
	 * @param idx index to remove
	 * @return true if successfully removed.
	 */
	public boolean remove(int idx) {
		return obj.remove(idx);
	}
	
	
	/**
	 * copy the set of items
	 * @return
	 */
	public Pack copy() {
		return new Pack(new HashSet<>(obj));
	}
	

	@Override
	public String toString() {
		return Arrays.toString(obj.toArray());
	}

	
	public String toBinaryString() {
		int[] binary = new int[obj.size()];
		for (int i = 0; i < obj.size(); i++) {
			if (this.isPicked(i)) binary[i] = 1;
		}
		return Arrays.toString(binary);
	}
	
	
	/**
	 * @param idx of item
	 * @return if item is picked or not
	 */
	public boolean isPicked(int idx) {
		return obj.contains(idx);
	}

	/**
	 * @return true if at least one item is picked
	 */
	public boolean isAnyPicked() {
		return !obj.isEmpty();
	}

	
	/**
	 * @param numOfItems number of items provided by the problem.
	 * @return all items which are not picked.
	 */
	public Set<Integer> getNotPickedItems(int numOfItems) {
		Set<Integer> hash = new HashSet<>();
		for (int i = 0; i < numOfItems; i++) {
			if (!obj.contains(i))
				hash.add(i);
		}
		return hash;
	}
	
	
	/**
	 * @return set of integer
	 */
	public Set<Integer> decode() {
		return obj;
	}
	
	
	/**
	 * Checks whether the pack plan is valid. 
	 * No items indices which are larger than the number of items are allowed.
	 */
	public void validate(int numOfItems) {

		for(int idx : obj) {
			// it is equal because there is an item 0
			if (idx >= numOfItems) throw new VariableNotValidException(String
					.format("There are only %s items for the problem, but item %s is picked. (Index starts with 0)", numOfItems, idx));
		}
	}
	
	
	/**
	 * @return number of items that are picked up
	 */
	public int numOfItems() {
		return obj.size();
	}
	

	
	public static Pack createFromString(String s) {
		return new Pack(new HashSet<>(StringUtil.parseAsIntegerList(s)));
	}
	
	public static Pack createFromBooleanString(String s) {
		return new Pack(StringUtil.parseAsBooleanList(s));
	}

	
	public static Pack empty() {
		return new Pack();
	}

	@Override
	public Pack build(Set<Integer> obj) {
		return new Pack(obj);
	}

	
	


	
}
