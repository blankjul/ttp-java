package com.msu.thief.evolving.tsp;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractMutation;
import com.msu.util.MyRandom;

public class SalesmanProblemMutation extends AbstractMutation<List<Point2D>> {

	@Override
	protected List<Point2D> mutate_(List<Point2D> cities, IProblem problem, MyRandom rand) {
		List<Point2D> result = new ArrayList<>();
		final double prob = 1 / (double) cities.size();
		for (int i = 0; i < cities.size(); i++) {
			if (rand.nextDouble() <  prob) {
				result.add(new Point2D.Double(rand.nextDouble(0, 1000), rand.nextDouble(0, 1000)));
			} else {
				result.add(cities.get(i));
			}
		}
		return result;

	}

}
