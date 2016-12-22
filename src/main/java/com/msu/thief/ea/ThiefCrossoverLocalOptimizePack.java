package com.msu.thief.ea;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.util.MyRandom;
import com.msu.thief.ea.factory.PackFullFactory;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefCrossoverLocalOptimizePack implements ICrossover<TTPVariable> {
	
	//! problem for the evaluations
	protected AbstractThiefProblem thief = null;

	//! crossover for the tour
	protected ICrossover<Tour> cTour = null;
	
	//! how long local optimize without improvement
	protected int max = 100;
	
	
	public ThiefCrossoverLocalOptimizePack(AbstractThiefProblem thief, ICrossover<Tour>cTour) {
		super();
		this.thief = thief;
		this.cTour = cTour;
	}
	
	

	@Override
	public List<TTPVariable> crossover(TTPVariable a, TTPVariable b, MyRandom rand) {
		
		List<TTPVariable> result = new ArrayList<>();
		
		// local optimize the pack for the tour
		for (Tour pi : cTour.crossover(a.getTour(), b.getTour(), rand)) {
			
			Pack z = new PackFullFactory(thief).next(rand);
			
			ISolution<TTPVariable> s = thief.evaluate(TTPVariable.create(pi, z));
			int counter = 0;
			
			
			while(counter < max) {
				
				if (s.getVariable().getPack().numOfItems() == thief.numOfItems()) break;
				
				Pack nextPack = new Pack(s.getVariable().getPack().decode());
				List<Integer> nextItems = new ArrayList<>(nextPack.getNotPickedItems(thief.getItems().size()));
				final int idx = rand.nextInt(nextItems.size());
				nextPack.add(nextItems.get(idx));
				
				ISolution<TTPVariable> next = thief.evaluate(TTPVariable.create(pi, nextPack));
				
				if (SolutionDominator.isDominating(next, s)) {
					System.out.println("YESS");
					counter = 0;
					s = next;
				}
				
				counter++;
			}
			
			result.add(s.getVariable());
		}
		
		return result;
	}



	
	
	
}
