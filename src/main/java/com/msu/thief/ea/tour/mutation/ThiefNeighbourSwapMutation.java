package com.msu.thief.ea.tour.mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class ThiefNeighbourSwapMutation implements TourMutation {

	@Override
	public void mutate(AbstractThiefProblem thief, MyRandom rand, Tour tour) {
		
		/*
		// get randomly a good city in the end of the tour
		List<Double> prob = getProbabilityDistribution(tour.encode().size());
		prob.set(1, 0.0);

		// city to swap
		int idx = selectCity(prob, rand);
		*/
		
		int idx = rand.nextInt(thief.numOfCities());

		List<Integer> neighbours = getNeighboursOfCity(thief.getMap(), idx, 5);
		int other = rand.select(neighbours);
		int otherIdx = tour.encode().indexOf(other);
		
		Util.swap(tour.encode(), idx, otherIdx);


	}

	public static List<Integer> getNeighboursOfCity(SymmetricMap m, int city, int numOfNeighbours) {
		List<Integer> index = Util.createIndex(1, m.getSize());
		double[] distances = m.getDistancesOfCity(city);
		Collections.sort(index, (Integer i1, Integer i2) -> Double.compare(distances[i1], distances[i2]));
		return index.subList(1, Math.min(index.size(), numOfNeighbours + 1));

	}

	public static List<Double> getProbabilityDistribution(int n) {
		List<Double> index = new ArrayList<>();
		final int sum = (n * (n - 1)) / 2;
		for (int j = 0; j < n; j++) {
			index.add(j / (double) sum);
		}
		return index;
	}

	public static int selectCity(List<Double> prob, MyRandom rand) {
		final double r = rand.nextDouble();
		int i = prob.size() - 1;
		for (; i >= 0; i--) {
			if (r > prob.get(i))
				break;
		}
		return i;
	}

	public static void main(String[] args) {

		MyRandom rand = new MyRandom(123);
		for (int i = 0; i < 30; i++) {
			List<Double> prob = getProbabilityDistribution(5);
			System.out.println(selectCity(prob, rand));
		}

		// SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem)
		// new
		// JsonThiefProblemReader().read("resources/my_publication_coordinates.ttp");

		// getNeighboursOfCity(problem.getMap(), 2, 2);

	}

}
