package com.msu.thief.ea.factory;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.AOperator;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;


public class TourRandomFactory extends AOperator implements IFactory<Tour> {

	public TourRandomFactory(AbstractThiefProblem thief) {
		super(thief);
	}

	

	@Override
	public Tour next(MyRandom rand) {
		List<Integer> tour = new ArrayList<>(thief.numOfCities());
		for (int i = 1; i < thief.numOfCities(); i++) {
			tour.add(i);
		}
		rand.shuffle(tour);
		tour.add(0, 0);
		return new Tour(tour);
	}

	
	@Override
	public boolean hasNext() {
		return true;
	}




}
