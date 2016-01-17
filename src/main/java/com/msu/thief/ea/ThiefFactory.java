package com.msu.thief.ea;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.thief.problems.variable.Pack;
import com.msu.thief.problems.variable.TTPVariable;
import com.msu.thief.problems.variable.Tour;

public class ThiefFactory implements IFactory<TTPVariable>{

	//! crossover for the tour
	protected IFactory<Tour> fTour = null;
	
	//! crossover for the packing plan
	protected IFactory<Pack> fPack = null;

	
	public ThiefFactory(IFactory<Tour> fTour, IFactory<Pack> fPack) {
		super();
		this.fTour = fTour;
		this.fPack = fPack;
	}
	
	
	public TTPVariable next(MyRandom rand) {
		return TTPVariable.create(fTour.next(rand), fPack.next(rand));
	}



	@Override
	public boolean hasNext() {
		return fTour.hasNext() && fPack.hasNext();
	}

	

	
}
