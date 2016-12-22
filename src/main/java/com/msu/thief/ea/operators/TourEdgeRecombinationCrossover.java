package com.msu.thief.ea.operators;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.moo.util.MyRandom;
import com.msu.thief.problems.variable.Tour;

public class TourEdgeRecombinationCrossover implements ICrossover<Tour> {


	@Override
	public List<Tour> crossover(Tour t1, Tour t2, MyRandom rand) {
		List<List<Integer>> off = new EdgeRecombinationCrossover<Integer>().crossover(t1.decode(), t2.decode(), rand);
		return Arrays.asList(new Tour(off.get(0)), new Tour(off.get(1)));
	}

}
