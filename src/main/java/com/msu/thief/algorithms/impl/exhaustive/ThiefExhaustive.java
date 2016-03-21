package com.msu.thief.algorithms.impl.exhaustive;

import java.util.List;

import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;
import com.msu.thief.util.Combination;
import com.msu.thief.util.CombinatorialUtil;

public class ThiefExhaustive {

	public static void run(SingleObjectiveThiefProblem problem) {
		final int numItems = problem.numOfItems();
		final int numCities = problem.numOfCities();

		// create the first tour
		// if starting city is should be zero start to permute at one
		List<Integer> index = CombinatorialUtil.getIndexVector(1, numCities);


		// over all possible tours
		for (List<Integer> l : CombinatorialUtil.permute(index)) {

			// since permute at one is enabled we need to add 0 as the first
			// city
			l.add(0, 0);
			Tour t = new Tour(l);

			// for all possible item combinations
			for (int i = 0; i <= numItems; i++) {
				Combination combination = new Combination(numItems, i);
				while (combination.hasNext()) {

					Pack p = new Pack(combination.next());
					Solution<TTPVariable> s = problem.evaluate(TTPVariable.create(t, p));
					if (!s.hasConstrainViolations()) System.out.println(s);
				}
			}
		}
	}

	public static int factorial(int n) {
		int fact = 1; // this will be the result
		for (int i = 1; i <= n; i++) {
			fact *= i;
		}
		return fact;
	}

}