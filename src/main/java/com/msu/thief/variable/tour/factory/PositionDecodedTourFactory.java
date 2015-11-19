package com.msu.thief.variable.tour.factory;

import java.util.ArrayList;
import java.util.LinkedList;

import com.msu.interfaces.IProblem;
import com.msu.thief.problems.ICityProblem;
import com.msu.thief.variable.tour.PositionDecodedTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Random;

/**
 * The StandardTour provides an implementation of a tour that saves directly the
 * permutation array.
 *
 * The encoding is nothing else than returning the array directly.
 *
 */
public class PositionDecodedTourFactory<P extends ICityProblem> extends ATourFactory {

	@Override
	public Tour<?> next_(IProblem p, Random rand) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < ((ICityProblem) p).numOfCities(); i++) {
			indices.add(rand.nextInt(0, i));
		}
		return new PositionDecodedTour(new ArrayList<>(indices));
	}



}
