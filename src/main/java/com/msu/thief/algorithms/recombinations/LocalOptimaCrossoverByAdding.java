package com.msu.thief.algorithms.recombinations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractCrossover;
import com.msu.thief.problems.ThiefProblemWithFixedTour;
import com.msu.thief.variable.pack.BooleanPackingList;
import com.msu.thief.variable.pack.IntegerSetPackingList;
import com.msu.util.MyRandom;

public class LocalOptimaCrossoverByAdding extends AbstractCrossover<List<Boolean>> {

	@Override
	protected List<List<Boolean>> crossover_(List<Boolean> a, List<Boolean> b, IProblem problem, MyRandom rand) {

		ThiefProblemWithFixedTour fixedTour = (ThiefProblemWithFixedTour) problem;

		Set<Integer> merge = new HashSet<>();
		merge.addAll(new BooleanPackingList(a).toIndexSet());
		merge.addAll(new BooleanPackingList(b).toIndexSet());

		addUntilNoImprovement(fixedTour, rand, merge);

		return Arrays.asList(new BooleanPackingList(merge, fixedTour.getProblem().numOfItems()).encode());
	}

	
	protected void addUntilNoImprovement(ThiefProblemWithFixedTour problem, MyRandom rand, Set<Integer> items) {

		IntegerSetPackingList b = new IntegerSetPackingList(items, problem.getProblem().numOfItems());

		double best = problem.evaluate(b).getObjectives(0);

		while (true) {

			List<Integer> possibleNext = new ArrayList<>();

			for (Integer idx : new HashSet<>(items)) {

				b.toIndexSet().add(idx);

				double fitness = problem.evaluate(b).getObjectives(0);
				if (fitness < best) possibleNext.add(idx);

				b.toIndexSet().remove(idx);
			}
			
			if (possibleNext.isEmpty()) break;
			else { items.add(rand.select(possibleNext));
			}

		}

	}

}
