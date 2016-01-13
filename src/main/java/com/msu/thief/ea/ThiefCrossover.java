package com.msu.thief.ea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.thief.ea.pack.crossover.PackCrossover;
import com.msu.thief.ea.tour.crossover.TourCrossover;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;
import com.msu.util.MyRandom;

public class ThiefCrossover {

	//! crossover for the tour
	protected TourCrossover cTour = null;
	
	//! crossover for the packing plan
	protected PackCrossover cPack = null;

	
	public ThiefCrossover(TourCrossover cTour, PackCrossover cPack) {
		super();
		this.cTour = cTour;
		this.cPack = cPack;
	}
	
	

	public List<TTPVariable> crossover(TTPVariable a, TTPVariable b, AbstractThiefProblem thief, MyRandom rand,
			IEvaluator evaluator) {
		
		
		double probTour = rand.nextDouble();
		double probPack = rand.nextDouble();
		
		// if both are lower and no mutation would occur -> do at least one mutation
		if (probTour > 0.5 && probPack > 0.5) {
			if (rand.nextDouble() < 0.5) probTour = 0;
			else probPack = 0;
		}
		
		// crossover of tours
		List<Tour> offTours = new ArrayList<>();
		if (cTour != null && probTour < 0.5) {
			offTours = cTour.crossover(thief, rand, a.getTour(), b.getTour());
		} else {
			offTours = Arrays.asList(a.getTour().copy(), b.getTour().copy());
		}
		
		// crossover of plans
		List<Pack> offPlans = new ArrayList<>();
		if (cPack != null && probPack < 0.5) {
			offPlans = cPack.crossover(thief, rand, a.getPack(), b.getPack());
		} else {
			offPlans = Arrays.asList(a.getPack().copy(), b.getPack().copy());
		}
		
		rand.shuffle(offTours);
		rand.shuffle(offPlans);

		
		List<TTPVariable> result = new ArrayList<>();
		
		for (int i = 0; i < offTours.size(); i++) {
			result.add(TTPVariable.create(offTours.get(i), offPlans.get(i)));
		}
		
		return result;
	}

	
	
	
}
