package com.msu.thief.model.tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.msu.thief.problems.ICityProblem;

public class StandardTourFactory<P extends ICityProblem> implements ITourFactory<P>{


	@Override
	public Tour<?> create(P p) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < p.numOfCities(); i++) {
			indices.add(i);
		}
		Collections.shuffle(indices);
		return new StandardTour(new ArrayList<>(indices));
	}

}
