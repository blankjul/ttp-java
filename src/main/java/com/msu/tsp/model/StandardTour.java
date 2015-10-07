package com.msu.tsp.model;

import java.util.ArrayList;
import java.util.List;

import com.msu.util.StringUtil;

/**
 * The StandardTour provides an implementation of a tour that saves directly the
 * permutation array.
 *
 * The encoding is nothing else than returning the array directly.
 *
 */
public class StandardTour extends Tour<List<Integer>> {

	
	/**
	 * Create a Tour using a permutation vector
	 * 
	 * @param list
	 *            tour represented by permutation vector
	 */
	public StandardTour(List<Integer> list) {
		super(list);
	}
	
	
	/**
	 * Create a tour by parsing a string.
	 * @param s string that contains the tour like [0,3,2,1]
	 */
	public StandardTour(String s) {
		super(StringUtil.parseAsIntegerList(s));
	}

	
	@Override
	public StandardTour copy() {
		return new StandardTour(new ArrayList<Integer>(obj));
	}

	@Override
	public List<Integer> encode() {
		return obj;
	}




}
