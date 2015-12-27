package com.msu.thief.variable.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.thief.util.StringUtil;

/**
 * Boolean packing list e.g. [0,0,1,0,0,1,0,1,0,0,0]
 *
 */
public class BooleanPackingList extends PackingList<List<Boolean>>{

	/**
	 * Initialize a packing list from boolean packing plan
	 */
	public BooleanPackingList(List<Boolean> l) {
		super(l);
	}
	
	/**
	 * Initialize from a string
	 */
	public BooleanPackingList(String s) {
		super(StringUtil.parseAsBooleanList(s));
	}
	
	public BooleanPackingList(Set<Integer> hash, int numOfItems) {
		super(new IntegerSetPackingList(hash, numOfItems).encode());
	}
	
	
	public BooleanPackingList(int numOfItems) {
		super(new ArrayList<>(numOfItems));
		for (int i = 0; i < numOfItems; i++) {
			obj.add(false);
		}
	}

	@Override
	public PackingList<List<Boolean>> copy() {
		return new BooleanPackingList(new ArrayList<Boolean>(obj));
	}

	@Override
	public List<Boolean> encode() {
		return obj;
	}
	
	
	@Override
	public Set<Integer> toIndexSet() {
		Set<Integer> indices = new HashSet<>();
		for (int i = 0; i < obj.size(); i++) {
			if (obj.get(i) == true) indices.add(i);
		}
		return indices;
	}

	
	@Override
	public String toString() {
		return Arrays.toString(toIndexSet().toArray());
	/*	List<Integer> p = new ArrayList<>();
		for (boolean b : obj) {
			int i = (b == true) ? 1 : 0;
			p.add(i);
		}
		return Arrays.toString(p.toArray());*/
	}
	
	public String toBinaryString() {
		List<Integer> p = new ArrayList<>();
		for (boolean b : obj) {
			int i = (b == true) ? 1 : 0;
			p.add(i);
		}
		return Arrays.toString(p.toArray());
	}

	@Override
	public boolean isPicked(int index) {
		return obj.get(index);
	}

	
	@Override
	public boolean isAnyPicked() {
		return obj.contains(true);
	}

	@Override
	public Set<Integer> getNotPickedItems() {
		Set<Integer> hash = new HashSet<>();
		for (int i = 0; i < obj.size(); i++) {
			if (!obj.get(i)) hash.add(i);
		}
		return hash;
	}
	

	


}
