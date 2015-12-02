package com.msu.thief.variable.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.thief.util.StringUtil;

public class BooleanPackingList extends PackingList<List<Boolean>>{

	public BooleanPackingList(List<Boolean> l) {
		super(l);
	}
	
	public BooleanPackingList(String s) {
		super(StringUtil.parseAsBooleanList(s));
	}
	
	public BooleanPackingList(Set<Integer> indices, int numOfItems) {
		super(new ArrayList<>());
		for (int i = 0; i < numOfItems; i++) {
			if (indices.contains(i)) obj.add(true);
			else obj.add(false);
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
		List<Integer> p = new ArrayList<>();
		for (boolean b : obj) {
			if (b == true)
				p.add(1);
			else
				p.add(0);
		}
		return Arrays.toString(p.toArray());
	}

	


}
