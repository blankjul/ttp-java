package com.msu.thief.ea;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.operators.TourSwapMutation;
import com.msu.thief.problems.MultiObjectiveThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefCrossoverLocalOptimizeTour implements ICrossover<TTPVariable> {
	

	//! crossover for the packing plan
	protected ICrossover<Pack> cPack = null;

	//! problem instances
	protected MultiObjectiveThiefProblem thief = null;
	
	//! optimal tour by lin kernighan
	protected Tour best = null;
	
	protected int max = 20;

	public ThiefCrossoverLocalOptimizeTour(ICrossover<Pack> cPack, MultiObjectiveThiefProblem thief, Tour best) {
		super();
		this.cPack = cPack;
		this.thief = thief;
		this.best = best;
	}




	@Override
	public List<TTPVariable> crossover(TTPVariable a, TTPVariable b, MyRandom rand) {
		
		List<TTPVariable> result = new ArrayList<>();
		List<Pack> offPlans = cPack.crossover(a.getPack(), b.getPack(), rand);
		
		for(Pack z : offPlans) {
			
			Tour pi = (rand.nextDouble() < 0.5) ? best : best.getSymmetric();
			ISolution<TTPVariable> s = thief.evaluate(TTPVariable.create(pi, z));
			
			// TODO: Check for infeasible solution
			
			int counter = 0;
			
			while (counter < max) {
				
				counter++;
				
				Tour current = new Tour(new ArrayList<>(s.getVariable().getTour().decode()));
				new TourSwapMutation().mutate(current, rand);
				
				ISolution<TTPVariable> next = thief.evaluate(TTPVariable.create(current, z));
				
				if (SolutionDominator.isDominating(next, s)) {
					counter = 0;
					s = next;
				}
				
			}
			
			result.add(s.getVariable());
			
		}
		
		return result;
	}



	
	
	
}
