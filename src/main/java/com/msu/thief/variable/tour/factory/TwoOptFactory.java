package com.msu.thief.variable.tour.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.util.Random;
import com.msu.problems.ICityProblem;
import com.msu.problems.SalesmanProblem;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;

public class TwoOptFactory extends ATourFactory{

	
	@Override
	public IVariable next(IProblem problem) {
		Tour<?> tour = new RandomTourFactory().next(problem);
		Tour<?> optimized = TwoOptFactory.optimize2Opt(new SalesmanProblem(((ICityProblem) problem).getMap()), tour);
		return optimized;
	}

	
	public static List<Integer> twoOptSwap(List<Integer> var, int i, int k) {

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
	
	

	public static Tour<?> optimize2Opt(IProblem p, Tour<?> t) {

		
		// always the best value for each iteration
		List<Integer> bestTour = t.encode();
		double bestTime = p.evaluate(new StandardTour(bestTour)).getObjectives(0);
		

		// define it for the while loop.
		Boolean hasImproved = null;
		
		do {
			
			// when the whole iteration starts set to false
			hasImproved = false;
			
			
			for (int i = 0; i < bestTour.size() - 1; i++) {
				for (int k = i + 2; k < bestTour.size() - 1; k++) {
					
					List<Integer> nextTour = twoOptSwap(bestTour,i,k);
					double nextTime = p.evaluate(new StandardTour(nextTour)).getObjectives(0);
					
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
		StandardTour std = new StandardTour(bestTour);
		
		if (Random.getInstance().nextDouble() < 0.5) {
			return std;
		} else {
			return std.getSymmetric();
		}
		
	}



}
