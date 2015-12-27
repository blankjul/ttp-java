package com.msu.thief.algorithms.recombinations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractCrossover;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.util.MyRandom;

public class LocalOptimaCrossoverByPruning extends AbstractCrossover<List<Boolean>> {

	@Override
	public List<List<Boolean>> crossover_(List<Boolean> a, List<Boolean> b, IProblem problem, MyRandom rand, IEvaluator eval) {

		ThiefProblemWithFixedTour fixedTour = (ThiefProblemWithFixedTour) problem;

		Set<Integer> merge = new HashSet<>();
		merge.addAll(new BooleanPackingList(a).toIndexSet());
		merge.addAll(new BooleanPackingList(b).toIndexSet());

		pruneRandomly(fixedTour, rand, merge);

		return Arrays.asList(new BooleanPackingList(merge, fixedTour.getProblem().numOfItems()).encode());
	}

	
	protected void pruneRandomly(ThiefProblemWithFixedTour problem, MyRandom rand, Set<Integer> items) {

		IntegerSetPackingList b = new IntegerSetPackingList(items, problem.getProblem().numOfItems());

		double best = problem.evaluate(b).getObjectives(0);

		while (true) {

			List<Integer> possiblePrunes = new ArrayList<>();

			for (Integer idx : new HashSet<>(items)) {

				b.toIndexSet().remove(idx);

				double fitness = problem.evaluate(b).getObjectives(0);
				if (fitness < best) possiblePrunes.add(idx);

				b.toIndexSet().add(idx);
			}
			
			if (possiblePrunes.isEmpty()) break;
			else { items.remove(rand.select(possiblePrunes));
			}

		}

	}

}
