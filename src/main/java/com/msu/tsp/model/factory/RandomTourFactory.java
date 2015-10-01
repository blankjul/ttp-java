package com.msu.tsp.model.factory;

import java.util.ArrayList;
import java.util.LinkedList;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.Random;
import com.msu.tsp.ICityProblem;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;

public class RandomTourFactory extends ATourFactory {


	@Override
	public Tour<?> next(IProblem p) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < ((ICityProblem) p).numOfCities(); i++) {
			indices.add(i);
		}
		Random.getInstance().shuffle(indices);
		return new StandardTour(new ArrayList<>(indices));
	}

}
