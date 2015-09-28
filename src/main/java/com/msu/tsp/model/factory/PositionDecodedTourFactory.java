package com.msu.tsp.model.factory;

import java.util.ArrayList;
import java.util.LinkedList;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.Random;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.model.PositionDecodedTour;
import com.msu.tsp.model.Tour;

/**
 * The StandardTour provides an implementation of a tour that saves directly the
 * permutation array.
 *
 * The encoding is nothing else than returning the array directly.
 *
 */
public class PositionDecodedTourFactory<P extends ICityProblem> extends ATourFactory {

	@Override
	public Tour<?> next(IProblem p) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < ((ICityProblem) p).numOfCities(); i++) {
			indices.add(Random.getInstance().nextInt(0, i));
		}
		return new PositionDecodedTour(new ArrayList<>(indices));
	}



}
