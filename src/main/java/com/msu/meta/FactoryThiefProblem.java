package com.msu.meta;

import java.util.Arrays;
import java.util.List;

import com.msu.algorithms.ExhaustiveThief;
import com.msu.analyze.DifferentToursInFront;
import com.msu.io.reader.JsonThiefReader;
import com.msu.moo.model.AProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.thief.ThiefProblem;

public class FactoryThiefProblem extends AProblem<FactoryThiefVariable>{

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

	@Override
	protected List<Double> evaluate_(FactoryThiefVariable variable) {
		ThiefProblem problem = variable.get();
		NonDominatedSolutionSet set = new ExhaustiveThief().run(problem);
		Integer numOfDifferentTour = new DifferentToursInFront().analyze(set);
		return Arrays.asList((double) numOfDifferentTour, -(double) set.size());
	}
	
	
	public static void main(String[] args) {
		ThiefProblem problem = new JsonThiefReader().read("../ttp-benchmark/opt_tour_performs_optimal.ttp");
		NonDominatedSolutionSet set = new ExhaustiveThief().run(problem);
		Integer numOfDifferentTour = new DifferentToursInFront().analyze(set);
		System.out.println(numOfDifferentTour);
		System.out.println(set.size());
	}
	

}
