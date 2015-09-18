package com.msu.thief.model.tour.factory;

import java.util.ArrayList;
import java.util.LinkedList;

import com.msu.moo.util.Random;
import com.msu.thief.model.tour.PositionDecodedTour;
import com.msu.thief.model.tour.Tour;
import com.msu.tsp.ICityProblem;

/**
 * The StandardTour provides an implementation of a tour that saves directly the
 * permutation array.
 *
 * The encoding is nothing else than returning the array directly.
 *
 */
public class PositionDecodedTourFactory<P extends ICityProblem> extends ATourFactory<P> {

	@Override
	public Tour<?> next(P p) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < p.numOfCities(); i++) {
			indices.add(Random.getInstance().nextInt(0, i));
		}
		return new PositionDecodedTour(new ArrayList<>(indices));
	}



}
