package com.msu.thief.variable.tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.interfaces.IVariable;
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
	 * Create a Tour using a permutation vector. Starting city is not given!!
	 * So the zero is added to the beginning.
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
	public List<Integer> encode() {
		return obj;
	}


	@Override
	public Tour<List<Integer>> getSymmetric() {
		List<Integer> tour = new ArrayList<>(obj.subList(1, obj.size()));
		Collections.reverse(tour);
		tour.add(0,0);
		return new StandardTour(tour);
	}


	@Override
	public IVariable copy() {
		return new StandardTour(new ArrayList<>(obj));
	}


	@Override
	public List<Integer> get() {
		//return new ArrayList<>(obj);
		return new ArrayList<>(obj.subList(1, obj.size()));
	}


	@Override
	public void set(Object obj) {
		try {
			@SuppressWarnings("unchecked")
			List<Integer> l = (List<Integer>) obj;
			l.add(0,0);
			this.obj = l;
		} catch (Exception e){
			throw new RuntimeException("Object could not be set for variable");
		}
	
	}

	
	
	
	
	



}
