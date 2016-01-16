package com.msu.thief.ea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.interfaces.ICrossover;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;

public class ThiefCrossover implements ICrossover<TTPVariable> {
	

	//! crossover for the tour
	protected ICrossover<Tour> cTour = null;
	
	//! crossover for the packing plan
	protected ICrossover<Pack> cPack = null;

	
	
	public ThiefCrossover(ICrossover<Tour>cTour, ICrossover<Pack> cPack) {
		super();
		this.cTour = cTour;
		this.cPack = cPack;
	}
	
	

	@Override
	public List<TTPVariable> crossover(TTPVariable a, TTPVariable b, MyRandom rand) {
		
		List<TTPVariable> result = new ArrayList<>();
		
		List<Tour> offTours = new ArrayList<>();
		List<Pack> offPlans = new ArrayList<>();
		
		
		double probTour = rand.nextDouble();
		double probPack = rand.nextDouble();
		
		
		// if both are lower and no mutation would occur -> do at least one mutation
		if (probTour > 0.5 && probPack > 0.5) {
			if (rand.nextDouble() < 0.5) probTour = 0;
			else probPack = 0;
		}
		

		// crossover of tours
		if (cTour != null && probTour < 0.5) {
			offTours = cTour.crossover(a.getTour(), b.getTour(), rand);
		} else {
			offTours = Arrays.asList(a.getTour().copy(), b.getTour().copy());
		}
		
		// crossover of plans
		if (cPack != null && probPack < 0.5) {
			offPlans = cPack.crossover(a.getPack(), b.getPack(), rand);
		} else {
			offPlans = Arrays.asList(a.getPack().copy(), b.getPack().copy());
		}
		
		rand.shuffle(offTours);
		rand.shuffle(offPlans);

		for (int i = 0; i < offTours.size(); i++) {
			result.add(TTPVariable.create(offTours.get(i), offPlans.get(i)));
		}
		
		return result;
	}



	
	
	
}
