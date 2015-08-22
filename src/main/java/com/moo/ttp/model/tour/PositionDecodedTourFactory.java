package com.moo.ttp.model.tour;

import java.util.ArrayList;
import java.util.LinkedList;

import com.moo.ttp.util.Rnd;
import com.msu.moo.model.interfaces.IFactory;

/**
 * The StandardTour provides an implementation of a tour that saves directly the
 * permutation array.
 *
 * The encoding is nothing else than returning the array directly.
 *
 */
public class PositionDecodedTourFactory implements IFactory<Tour<?>> {

	//! length of the tour
	protected int length;
	
	public PositionDecodedTourFactory(int length) {
		super();
		this.length = length;
	}


	@Override
	public Tour<?> create() {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < length; i++) {
			indices.add(Rnd.rndInt(0, i));
		}
		return new PositionDecodedTour(new ArrayList<>(indices));
	}




}
