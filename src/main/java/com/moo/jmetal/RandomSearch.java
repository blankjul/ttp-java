package com.moo.jmetal;

import org.uma.jmetal.problem.Problem;

import com.moo.ttp.jmetal.jISolution;

public class RandomSearch extends org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearch<jISolution>{

	
	
	public RandomSearch(Problem<jISolution> p, int maxEvaluations) {
		super(p,maxEvaluations);
	}
	
	public String toString() {
		return "Random-" + getMaxEvaluations();
	}

}
