package com.msu.thief.algorithms.exhaustive;

import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.SalesmanProblem;
import com.msu.thief.util.CombinatorialUtil;
import com.msu.thief.variable.tour.StandardTour;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.Random;

public class SalesmanExhaustive extends AExhaustiveAlgorithm {


	
	
	@Override
	public NonDominatedSolutionSet run_(IProblem p, IEvaluator eval, Random rand) {

		SalesmanProblem problem = (SalesmanProblem) p;
		NonDominatedSolutionSet set = (onlyNonDominatedPoints) ? new NonDominatedSolutionSet() : new ExhaustiveSolutionSet();
		
		final int numCities = problem.numOfCities();
		
		// create the first tour
		// if starting city is should be zero start to permute at one
		List<Integer> index = CombinatorialUtil.getIndexVector(1,numCities);

		// over all possible tours
		for (List<Integer> l : CombinatorialUtil.permute(index)) {

			// since permute at one is enabled we need to add 0 as the first city
			l.add(0,0);
			Tour<?> t = new StandardTour(l);
			Solution solutionToAdd = eval.evaluate(p, t);
			//System.out.println(solutionToAdd);
			set.add(solutionToAdd);
			
		}
		return set;
	}


	
}
