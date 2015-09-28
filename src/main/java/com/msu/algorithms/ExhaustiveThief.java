package com.msu.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.knp.model.BooleanPackingList;
import com.msu.knp.model.PackingList;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.ThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;
import com.msu.util.Combination;
import com.msu.util.CombinatorialUtil;

public class ExhaustiveThief extends AbstractAlgorithm {

	public static int factorial(int n) {
		int fact = 1; // this will be the result
		for (int i = 1; i <= n; i++) {
			fact *= i;
		}
		return fact;
	}

	@Override
	public NonDominatedSolutionSet run(IEvaluator eval) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		ThiefProblem problem = (ThiefProblem) eval.getProblem();

		final int numItems = problem.numOfItems();
		final int numCities = problem.numOfCities();

		double numOfSolution = ExhaustiveThief.factorial(numCities) * Math.pow(2, numItems);
		System.out.println(String.format("There are %s solutions.", numOfSolution));

		// create the first tour
		List<Integer> index = CombinatorialUtil.getIndexVector(numCities);

		int counter = 0;

		// over all possible tours
		for (List<Integer> l : CombinatorialUtil.permute(index)) {

			Tour<?> t = new StandardTour(l);

			// for all possible item combinations
			for (int i = 0; i <= numItems; i++) {
				Combination combination = new Combination(numItems, i);
				while (combination.hasNext()) {
					int[] entries = combination.next();
					PackingList<?> b = new BooleanPackingList(convert(entries, numItems));
					TTPVariable var = new TTPVariable(t, b);
					set.add(eval.evaluate(var));
					if (++counter % 100000 == 0)
						System.out.println(String.format("%f perc.", (Double.valueOf(counter) / numOfSolution) * 100));
				}
			}
		}
		return set;
	}

	private List<Boolean> convert(int[] entries, int numItems) {
		List<Boolean> b = new ArrayList<>();
		for (int j = 0; j < numItems; j++)
			b.add(false);

		for (int entry : entries)
			b.set(entry, true);
		return b;
	}

}
