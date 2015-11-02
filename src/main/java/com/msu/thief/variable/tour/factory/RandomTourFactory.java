package com.msu.thief.variable.tour.factory;

import java.util.ArrayList;
import java.util.LinkedList;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.Random;
import com.msu.problems.ICityProblem;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;

public class RandomTourFactory extends ATourFactory {


	@Override
	public Tour<?> next_(IProblem p, Random rand) {
		LinkedList<Integer> indices = new LinkedList<Integer>();
		for (int i = 0; i < ((ICityProblem) p).numOfCities(); i++) {
			indices.add(i);
		}
		rand.shuffle(indices);
		return new StandardTour(new ArrayList<>(indices));
	}

}
