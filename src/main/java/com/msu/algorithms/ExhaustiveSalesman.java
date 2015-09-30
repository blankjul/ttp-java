package com.msu.algorithms;

import java.util.List;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.tsp.TravellingSalesmanProblem;
import com.msu.tsp.model.StandardTour;
import com.msu.tsp.model.Tour;
import com.msu.util.CombinatorialUtil;

public class ExhaustiveSalesman extends AbstractAlgorithm {


	@Override
	public NonDominatedSolutionSet run(IEvaluator eval) {

		TravellingSalesmanProblem problem = (TravellingSalesmanProblem) eval.getProblem();
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		
		final int numCities = problem.numOfCities();
		
		// create the first tour
		// if starting city is should be zero start to permute at one
		List<Integer> index = CombinatorialUtil.getIndexVector(1,numCities);

		// over all possible tours
		for (List<Integer> l : CombinatorialUtil.permute(index)) {

			// since permute at one is enabled we need to add 0 as the first city
			l.add(0,0);
			Tour<?> t = new StandardTour(l);
			Solution solutionToAdd = eval.evaluate(t);
			System.out.println(solutionToAdd);
			set.add(solutionToAdd);
			
		}
		return set;
	}


	
}
