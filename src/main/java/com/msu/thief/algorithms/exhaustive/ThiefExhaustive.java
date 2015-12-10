package com.msu.thief.algorithms.exhaustive;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.ThiefProblem;
import com.msu.thief.util.Combination;
import com.msu.thief.util.CombinatorialUtil;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.thief.variable.pack.PackingList;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class ThiefExhaustive extends AExhaustiveAlgorithm {

	
	public static int factorial(int n) {
		int fact = 1; // this will be the result
		for (int i = 1; i <= n; i++) {
			fact *= i;
		}
		return fact;
	}

	@Override
	public NonDominatedSolutionSet run_(IProblem p, IEvaluator eval, MyRandom rand) {
		
		boolean startingCityIsZero = ((ThiefProblem)p).isStartingCityIsZero();
		
		NonDominatedSolutionSet set = (onlyNonDominatedPoints) ? new NonDominatedSolutionSet() : new ExhaustiveSolutionSet();

		ThiefProblem problem = (ThiefProblem) p;

		final int numItems = problem.numOfItems();
		final int numCities = problem.numOfCities();
		
		double numOfSolution = -1;
		if (startingCityIsZero) numOfSolution = ThiefExhaustive.factorial(numCities-1) * Math.pow(2, numItems);
		else numOfSolution = ThiefExhaustive.factorial(numCities) * Math.pow(2, numItems);
		//System.out.println(String.format("There are %s solutions.", numOfSolution));

		// create the first tour
		// if starting city is should be zero start to permute at one
		int n = (startingCityIsZero) ? 1 : 0;
		List<Integer> index = CombinatorialUtil.getIndexVector(n,numCities);

		int counter = 0;

		// over all possible tours
		for (List<Integer> l : CombinatorialUtil.permute(index)) {

			// since permute at one is enabled we need to add 0 as the first city
			if (startingCityIsZero) l.add(0,0);
			Tour<?> t = new StandardTour(l);
			
			// for all possible item combinations
			for (int i = 0; i <= numItems; i++) {
				Combination combination = new Combination(numItems, i);
				while (combination.hasNext()) {
					
					Set<Integer> entries = new HashSet<>(combination.next());
					PackingList<?> b = new IntegerSetPackingList(entries, problem.numOfItems());
					
					TTPVariable var = new TTPVariable(t, b);
					Solution s = eval.evaluate(p, var);
					set.add(s);
					if (++counter % 100000 == 0)
						System.out.println(String.format("%f perc.", (Double.valueOf(counter) / numOfSolution) * 100));
				}
			}
		}
		return set;
	}


	
}
