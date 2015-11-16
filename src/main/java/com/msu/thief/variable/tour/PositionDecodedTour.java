package com.msu.thief.variable.tour;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The StandardTour provides an implementation of a tour that saves directly the
 * permutation array.
 *
 * The encoding is nothing else than returning the array directly.
 *
 */
public class PositionDecodedTour extends Tour<List<Integer>> {

	
	/**
	 * Create a Tour using a permutation vector
	 * 
	 * @param list
	 *            tour represented by permutation vector
	 */
	public PositionDecodedTour(List<Integer> list) {
		super(list);
	}


	
	@Override
	public PositionDecodedTour copy() {
		return new PositionDecodedTour(new ArrayList<Integer>(obj));
	}


	@Override
	public List<Integer> encode() {
		LinkedList<Integer> result = new LinkedList<>();
		int value = 0;
		for(Integer i : this.obj) {
			if (i > value) throw new RuntimeException("Error while decoding the tour!");
			result.add(i, value);
			value++;
		}
		return result;
	}



	@Override
	public Tour<List<Integer>> getSymmetric() {
		throw new RuntimeException("Not implemented so far!");
	}




}
