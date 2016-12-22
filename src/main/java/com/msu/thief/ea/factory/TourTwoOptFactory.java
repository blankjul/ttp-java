package com.msu.thief.ea.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Pair;
import com.msu.thief.model.SymmetricMap;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.problems.variable.Tour;

public class TourTwoOptFactory  implements IFactory<Tour> {

	
	protected AbstractThiefProblem thief;
	
	

	public TourTwoOptFactory(AbstractThiefProblem thief) {
		super();
		this.thief = thief;
	}



	@Override
	public Tour next(MyRandom rand) {
		Tour tour = new TourRandomFactory(thief).next(rand);
		Tour optimized = TourTwoOptFactory.optimize2Opt(new SalesmanProblem(thief.getMap()), tour, rand);
		return optimized;
	}


	

	
	public static Pair<List<Integer>, Double> twoOptSwap(List<Integer> var, int i, int k, SymmetricMap map, double time) {
		List<Integer> tour = twoOptSwap(var, i, k);
		double resTime = time - map.get(var.get(i-1),var.get(i)) - map.get(var.get(k), var.get(k+1)) +
				map.get(var.get(i-1), var.get(k)) + map.get(var.get(i), var.get(k+1));
		return Pair.create(tour, resTime);
	}
	
	
	
	public static List<Integer> twoOptSwap(List<Integer> var, int i, int k) {
		if (i <= 0) 
			throw new RuntimeException(String.format("i=%s has to be lower than 0!", i));
		if (i > k)
			throw new RuntimeException(String.format("i=%s has to be lower than k=%s!", i, k));
		if (k + 1 > var.size())
			throw new RuntimeException(String.format("k=%s is not allowed to be larger than size of tour -1!", k, var.size()));

		List<Integer> tour = new ArrayList<>(var);
		
		List<Integer> result = new ArrayList<>();
		result.addAll(tour.subList(0, i));

		List<Integer> middle = tour.subList(i, k+1);
		Collections.reverse(middle);
		result.addAll(middle);

		result.addAll(tour.subList(k+1, tour.size()));

		return result;
	}
	
	
	public static Tour optimize2Opt(SalesmanProblem p, Tour t, MyRandom rand) {
		return optimize2Opt(p, t, rand, true);
	}
	
	
	public static Tour optimize2Opt(SalesmanProblem p, Tour t, MyRandom rand, boolean useFastCalc) {

		
		// always the best value for each iteration
		List<Integer> bestTour = t.decode();
		double bestTime = p.evaluate(new Tour(bestTour)).getObjective(0);
		

		// define it for the while loop.
		Boolean hasImproved = null;
		
		do {
			
			// when the whole iteration starts set to false
			hasImproved = false;
			
			
			for (int i = 1; i < bestTour.size() - 1; i++) {
				for (int k = i + 2; k < bestTour.size() - 1; k++) {
					
					Pair<List<Integer>, Double> pair = twoOptSwap(bestTour,i,k, p.getMap(), bestTime);
					List<Integer> nextTour = pair.first;
					
					double nextTime = (useFastCalc) ? pair.second : p.evaluate(new Tour(nextTour)).getObjective(0);
					
					if (nextTime < bestTime) {
						
						// if new best solution found set to true
						hasImproved = true;
						
						// Improvement found so reset
						bestTour = nextTour;
						bestTime = nextTime;
						
					}
					
				}
			}
			
			
		} while (hasImproved);

		Collections.rotate(bestTour, -bestTour.indexOf(0));
		Tour std = new Tour(bestTour);
		
		if (rand.nextDouble() < 0.5) {
			return std;
		} else {
			return std.getSymmetric();
		}
		
	}



}