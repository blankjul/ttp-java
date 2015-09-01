package com.msu.thief.model.tour;

import java.util.ArrayList;
import java.util.LinkedList;

import com.msu.moo.util.Random;
import com.msu.thief.problems.ICityProblem;

/**
 * The StandardTour provides an implementation of a tour that saves directly the
 * permutation array.
 *
 * The encoding is nothing else than returning the array directly.
 *
 */
public class PositionDecodedTourFactory<P extends ICityProblem> implements ITourFactory<P> {

	@Override
	public Tour<?> create(P p) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < p.numOfCities(); i++) {
			indices.add(Random.getInstance().nextInt(0, i));
		}
		return new PositionDecodedTour(new ArrayList<>(indices));
	}



}
