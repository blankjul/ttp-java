package com.msu.tsp.model.factory;

import java.util.ArrayList;
import java.util.LinkedList;

import com.msu.moo.util.Random;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;

public class RandomFactory<P extends ICityProblem> extends ATourFactory<P>{


	@Override
	public Tour<?> next(P p) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < p.numOfCities(); i++) {
			indices.add(i);
		}
		Random.getInstance().shuffle(indices);
		return new StandardTour(new ArrayList<>(indices));
	}

}
