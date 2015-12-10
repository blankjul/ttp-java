package com.msu.thief.variable.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.util.Pair;

/**
 * This class represents a packing list by the integer indices which are packed.
 * 
 * e.g. {2,5,7} is equal to [0,0,1,0,0,1,0,1,0,0,0]
 * 
 * if there are 10 items. The amount of items needs to be known to fill up with
 * zeros until we reach the final item.
 */
public class IntegerSetPackingList extends PackingList<Pair<Set<Integer>, Integer>> {


	/**
	 * Initialize an empty packing list
	 */
	public IntegerSetPackingList(int numOfItems) {
		this(new HashSet<>(), numOfItems);
	}
	
	
	/**
	 * Initialize a packing list with a set and number of items
	 */
	public IntegerSetPackingList(Set<Integer> l, int numOfItems) {
		super(Pair.create(l, numOfItems));
	}
	
	/**
	 * Initialize a packing list from boolean packing plan
	 */
	public IntegerSetPackingList(List<Boolean> l) {
		super(Pair.create(new HashSet<>(), l.size()));
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i)) obj.first.add(i);
		}
	}

	@Override
	public IntegerSetPackingList copy() {
		return new IntegerSetPackingList(new HashSet<Integer>(obj.first), obj.second);
	}
	

	@Override
	public List<Boolean> encode() {
		List<Boolean> indices = new ArrayList<>(obj.second);
		for (int i = 0; i < obj.second; i++) {
			indices.add(obj.first.contains(i));
		}
		return indices;
	}

	@Override
	public Set<Integer> toIndexSet() {
		return obj.first;
	}

	@Override
	public String toString() {
		return Arrays.toString(obj.first.toArray());
	}

	@Override
	public boolean isPicked(int index) {
		return obj.first.contains(index);
	}

	@Override
	public boolean isAnyPicked() {
		return !obj.first.isEmpty();
	}

	@Override
	public Set<Integer> getNotPickedItems() {
		Set<Integer> hash = new HashSet<>();
		for (int i = 0; i < obj.second; i++) {
			if (!obj.first.contains(i))
				hash.add(i);
		}
		return hash;
	}
	
	
	

}
