package com.msu.thief.model.tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.msu.thief.problems.TravellingThiefProblem;

public class StandardTourFactory implements ITourFactory {


	@Override
	public Tour<?> create(TravellingThiefProblem p) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < p.numOfCities(); i++) {
			indices.add(i);
		}
		Collections.shuffle(indices);
		return new StandardTour(new ArrayList<>(indices));
	}

}
