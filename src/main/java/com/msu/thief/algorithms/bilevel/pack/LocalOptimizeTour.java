package com.msu.thief.algorithms.bilevel.pack;

import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.problems.ThiefProblemWithFixedPacking;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class LocalOptimizeTour extends AbstractSingleObjectiveDomainAlgorithm<ThiefProblemWithFixedPacking> {


	protected Tour<?> tour;
	
	
	
	public LocalOptimizeTour(Tour<?> initialTour) {
		super();
		this.tour = initialTour;
	}



	@Override
	public Solution run___(ThiefProblemWithFixedPacking problem, IEvaluator evaluator, MyRandom rand) {
		
		List<Integer> l = tour.encode();
		
		for (int i = l.size(); i > 0; i--) {
			
			for (int j = l.size(); j > 0; j--) {
				
				
			}
			
		}
		
		
		
		
		return null;
	}
	
	

}
