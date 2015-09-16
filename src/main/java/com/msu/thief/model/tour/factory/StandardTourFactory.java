package com.msu.thief.model.tour.factory;

import java.util.ArrayList;
import java.util.LinkedList;

import com.msu.moo.util.Random;
import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.tsp.ICityProblem;

public class StandardTourFactory<P extends ICityProblem> implements ITourFactory<P>{


	@Override
	public Tour<?> create(P p) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < p.numOfCities(); i++) {
			indices.add(i);
		}
		Random.getInstance().shuffle(indices);
		return new StandardTour(new ArrayList<>(indices));
	}

}
