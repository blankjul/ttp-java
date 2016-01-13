package com.msu.thief.ea;

import com.msu.interfaces.IEvaluator;
import com.msu.thief.ea.pack.mutation.PackMutation;
import com.msu.thief.ea.tour.mutation.TourMutation;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.util.MyRandom;

public class TTPMutation {

	//! crossover for the tour
	protected TourMutation mTour;
	
	//! crossover for the packing plan
	protected PackMutation mPack;

	
	public TTPMutation(TourMutation mTour, PackMutation mPack) {
		super();
		this.mTour = mTour;
		this.mPack = mPack;
	}

	
	public void mutate(TTPVariable a, AbstractThiefProblem thief, MyRandom rand, IEvaluator eval) {
		
		
		double probTour = rand.nextDouble();
		double probPack = rand.nextDouble();
		
		// if both are lower and no mutation would occur -> do at least one mutation
		if (probTour > 0.5 && probPack > 0.5) {
			if (rand.nextDouble() < 0.5) probTour = 0;
			else probPack = 0;
		}
		

		if (mTour != null && probTour < 0.5) {
			mTour.mutate(thief, rand, a.getTour());
		}
		
		if (mPack != null && probPack < 0.5) {
			mPack.mutate(thief, rand, a.getPack());
		}

		
	}
	
	
	
	
	
}
