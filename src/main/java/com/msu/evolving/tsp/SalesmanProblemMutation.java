package com.msu.evolving.tsp;

import java.awt.geom.Point2D;
import java.util.List;

import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.util.Random;

public class SalesmanProblemMutation extends AbstractMutation<List<Point2D>> {

	@Override
	protected void mutate_(List<Point2D> cities, Random rand) {
		final double prob = 1 / (double) cities.size();
		for (int i = 0; i < cities.size(); i++) {
			if (rand.nextDouble() <  prob) {
				cities.set(i, new Point2D.Double(rand.nextDouble(0, 1000), rand.nextDouble(0, 1000)));
			}
		}

	}

}
