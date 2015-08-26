package com.msu.thief.model.tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.msu.thief.variable.TravellingThiefProblem;

public class StandardTourFactory implements ITourFactory {


	@Override
	public Tour<?> create(TravellingThiefProblem p) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 1; i < p.numOfCities(); i++) {
			indices.add(i);
		}
		Collections.shuffle(indices);
		indices.addFirst(0);
		return new StandardTour(new ArrayList<>(indices));
	}

}
