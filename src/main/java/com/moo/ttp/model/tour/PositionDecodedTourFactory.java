package com.moo.ttp.model.tour;

import java.util.ArrayList;
import java.util.LinkedList;

import com.moo.ttp.util.Rnd;
import com.moo.ttp.variable.TravellingThiefProblem;

/**
 * The StandardTour provides an implementation of a tour that saves directly the
 * permutation array.
 *
 * The encoding is nothing else than returning the array directly.
 *
 */
public class PositionDecodedTourFactory implements ITourFactory {

	@Override
	public Tour<?> create(TravellingThiefProblem p) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < p.numOfCities(); i++) {
			indices.add(Rnd.rndInt(0, i));
		}
		return new PositionDecodedTour(new ArrayList<>(indices));
	}




}
