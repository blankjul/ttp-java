package com.msu.thief.ea.operators;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.util.MyRandom;
import com.msu.thief.problems.variable.Tour;

public class TourFixedCrossover implements ICrossover<Tour> {


	@Override
	public List<Tour> crossover(Tour t1, Tour t2, MyRandom rand) {
		List<Tour> off = new ArrayList<>();
		off.add(new Tour(new ArrayList<>(t1.decode())));
		off.add(new Tour(new ArrayList<>(t2.decode())));
		return off;
	}
	
	

}
