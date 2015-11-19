package com.msu.thief.variable.pack;

import java.util.ArrayList;
import java.util.List;

import com.msu.thief.util.StringUtil;

public class BooleanPackingList extends PackingList<List<Boolean>>{

	public BooleanPackingList(List<Boolean> l) {
		super(l);
	}
	
	public BooleanPackingList(String s) {
		super(StringUtil.parseAsBooleanList(s));
	}

	@Override
	public PackingList<List<Boolean>> copy() {
		return new BooleanPackingList(new ArrayList<Boolean>(obj));
	}

	@Override
	public List<Boolean> encode() {
		return obj;
	}



}
