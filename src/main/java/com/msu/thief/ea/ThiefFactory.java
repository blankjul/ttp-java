package com.msu.thief.ea;

import com.msu.thief.ea.pack.factory.PackFactory;
import com.msu.thief.ea.tour.factory.TourFactory;
import com.msu.thief.problems.AbstractThiefProblem;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.util.MyRandom;

public class ThiefFactory implements Factory<TTPVariable>{

	//! crossover for the tour
	protected TourFactory fTour = null;
	
	//! crossover for the packing plan
	protected PackFactory fPack = null;

	
	public ThiefFactory(TourFactory cTour, PackFactory cPack) {
		super();
		this.fTour = cTour;
		this.fPack = cPack;
	}
	

	@Override
	public TTPVariable create() {
		return TTPVariable.create(fTour.create(), fPack.create());
	}



	@Override
	public boolean hasNext() {
		return fTour.hasNext() && fPack.hasNext();
	}


	@Override
	public void initialize(AbstractThiefProblem problem, MyRandom rand) {
		fTour.initialize(problem, rand);
		fPack.initialize(problem, rand);
	}



	
}
