package com.moo.ttp.model.tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The StandardTour provides an implementation of a tour that saves directly the
 * permutation array.
 *
 * The encoding is nothing else than returning the array directly.
 *
 */
public class StandardTour extends ATour<List<Integer>> {

	
	/**
	 * Create a Tour using a permutation vector
	 * 
	 * @param list
	 *            tour represented by permutation vector
	 */
	public StandardTour(List<Integer> list) {
		super();
		this.obj = list;
	}

	/**
	 * Create a random tour with n cities. The first city is always the city 0!
	 */
	@Override
	public ATour<List<Integer>> random(int numOfCities) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 1; i < numOfCities; i++) {
			indices.add(i);
		}
		Collections.shuffle(indices);
		indices.addFirst(0);
		return new StandardTour(new ArrayList<>(indices));
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
