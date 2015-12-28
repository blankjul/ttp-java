package com.msu.thief.algorithms.recombinations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractMutation;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class TTPNeighbourSwapMutation extends AbstractMutation<List<Integer>>{


	@Override
	public List<Integer> mutate_(List<Integer> tour, IProblem problem, MyRandom rand, IEvaluator evaluator) {
		
		AbstractThiefProblem thief = (AbstractThiefProblem) problem;
		
		// get randomly a good city in the end of the tour
		List<Double> prob = getProbabilityDistribution(tour.size());
		prob.set(1, 0.0);

		// city to swap
		int idx = selectCity(prob, rand);
		
		List<Integer> neighbours = getNeighboursOfCity(thief.getMap(), idx, 5);
		int other = rand.select(neighbours);
		
		List<Integer> result = new ArrayList<>(tour);
		//System.out.println(String.format("%s %s %s",tour, idx, other));
		Util.swap(result, tour.indexOf(idx), tour.indexOf(other));
		
		return result;
	}
	
	
	protected static List<Integer> getNeighboursOfCity(SymmetricMap m, int city, int numOfNeighbours) {
		List<Integer> index = Util.createIndex(1, m.getSize());
		double[] distances =  m.getDistancesOfCity(city);
		Collections.sort(index, (Integer i1, Integer i2) -> Double.compare(distances[i1], distances[i2]));
		return index.subList(1, Math.min(index.size(), numOfNeighbours));
		
	}
	
	protected static List<Double> getProbabilityDistribution(int n) {
		List<Double> index = new ArrayList<>();
		final int sum = (n * (n - 1)) / 2;
		for (int j = 0; j < n; j++) {
			index.add(j / (double) sum);
		}
		return index;
	}
	
	
	protected static int selectCity(List<Double> prob, MyRandom rand) {
		final double r = rand.nextDouble();
		int i = prob.size() - 1;
		for (; i >= 0; i--) {
			if (r > prob.get(i)) break;
		}
		return i;
	}
	
	
	public static void main(String[] args) {
		
		
		MyRandom rand = new MyRandom(123);
		for (int i = 0; i < 30; i++) {
			List<Double> prob = getProbabilityDistribution(5);
			System.out.println(selectCity(prob, rand));
		}
		
		//SingleObjectiveThiefProblem problem = (SingleObjectiveThiefProblem) new JsonThiefProblemReader().read("resources/my_publication_coordinates.ttp");
		
		//getNeighboursOfCity(problem.getMap(), 2, 2);
		
	}
	
	
}
